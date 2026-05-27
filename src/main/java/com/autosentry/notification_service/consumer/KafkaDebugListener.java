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
        log.debug("Kafka listener idle: {}", event);
    }

    @EventListener
    public void onConsumerStopped(ConsumerStoppedEvent event) {
        log.error("Kafka consumer stopped: {}", event);
    }
}