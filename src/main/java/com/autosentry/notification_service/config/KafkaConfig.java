package com.autosentry.notification_service.config;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehicleExpiryEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, VehicleExpiryEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, VehicleExpiryEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (ConsumerRecord<?, ?> record, Exception exception) ->
                        log.error(
                                "Failed to process Kafka record topic={} partition={} offset={}",
                                record.topic(),
                                record.partition(),
                                record.offset(),
                                exception
                        ),
                new FixedBackOff(1000L, 3L)
        );
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}
