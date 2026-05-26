package com.autosentry.notification_service.consumer;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import com.autosentry.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

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

        System.out.println("\n========== KAFKA DEBUG ==========");
        System.out.println("Topic   : " + topic);
        System.out.println("Offset  : " + offset);
        System.out.println("Event   : " + event);
        System.out.println("=================================\n");

        emailService.sendExpiryEmail(event);
    }
}