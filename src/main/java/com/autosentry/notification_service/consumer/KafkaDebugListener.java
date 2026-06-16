package com.autosentry.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaDebugListener {

    @EventListener
    public void onIdle(ListenerContainerIdleEvent event) {
        // Log when the Kafka consumer is sitting idle waiting for events (kept as debug to avoid log spam)
        log.debug("⏸️ Kafka listener container is idle. Waiting for new events... [{}]", event);
    }

    @EventListener
    public void onConsumerStopped(ConsumerStoppedEvent event) {
        // Highly visible error log if the consumer drops its connection or crashes
        log.error("🚨 CRITICAL ALERT: Kafka consumer has stopped! This will halt notification processing. Details: {}", event);
    }
}