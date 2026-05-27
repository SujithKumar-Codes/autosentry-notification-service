package com.autosentry.notification_service.consumer;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import com.autosentry.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleExpiryConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "vehicle-expiry-topic",
            groupId = "notification-group"
    )
    public void consume(
            VehicleExpiryEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {

        log.info("Consumed vehicle-expiry event topic={} offset={} plate={} type={} daysLeft={}",
                topic, offset, event.getPlateNumber(), event.getEventType(), event.getDaysLeft());

        emailService.sendExpiryEmail(event);
    }
}