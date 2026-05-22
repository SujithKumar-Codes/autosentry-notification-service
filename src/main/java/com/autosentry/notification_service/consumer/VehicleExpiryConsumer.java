package com.autosentry.notification_service.consumer;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VehicleExpiryConsumer {

    @KafkaListener(topics = "vehicle-expiry-topic")
    public void consume(VehicleExpiryEvent event) {

        System.out.println(event);
    }
}