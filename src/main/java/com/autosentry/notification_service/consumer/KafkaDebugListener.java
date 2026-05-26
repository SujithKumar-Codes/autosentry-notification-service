import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.event.ListenerContainerConsumerFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaDebugListener {

    @EventListener
    public void handle(org.springframework.kafka.event.ListenerContainerIdleEvent event) {
        log.info("🟡 Kafka Listener Idle Event: {}", event);
    }

    @EventListener
    public void handle(org.springframework.kafka.event.ListenerContainerStoppedEvent event) {
        log.error("🔴 Kafka Listener STOPPED: {}", event);
    }

    @EventListener
    public void handle(org.springframework.kafka.event.ConsumerStoppedEvent event) {
        log.error("🔴 Consumer STOPPED: {}", event);
    }
}