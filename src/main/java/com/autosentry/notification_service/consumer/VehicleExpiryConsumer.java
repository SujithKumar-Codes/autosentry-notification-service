package com.autosentry.notification_service.consumer;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import com.autosentry.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleExpiryConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "vehicle-expiry-topic")
    public void consume(VehicleExpiryEvent event) {

        System.out.println(event);

        emailService.sendExpiryEmail(event);
    }
}