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

        log.info("📥 INCOMING KAFKA EVENT: Consuming from topic '{}' [Offset: {}]", topic, offset);
        log.info("Event Details - Plate: {}, Type: {}, Days Left: {}", event.getPlateNumber(), event.getEventType(), event.getDaysLeft());

        try {
            emailService.sendExpiryEmail(event);
            log.info("✅ SUCCESSFULLY PROCESSED KAFKA EVENT: Notification workflow completed for Plate: {}", event.getPlateNumber());
        } catch (Exception e) {
            log.error("❌ FAILED TO PROCESS KAFKA EVENT: Email dispatch failed for Plate: {}. Reason: {}", event.getPlateNumber(), e.getMessage(), e);
            // Re-throw so Kafka knows the consumption failed and can trigger retries or send to a Dead Letter Queue (DLQ)
            throw e;
        }
    }
}