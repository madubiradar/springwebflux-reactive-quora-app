package com.reactive.quora.producer;

import com.reactive.quora.config.KafkaConfig;
import com.reactive.quora.events.ViewCountEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishViewCountEvent(ViewCountEvent viewCountEvent){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetId(), viewCountEvent)
                .whenComplete((result, error) -> {
                   if(error ==null){
                       System.out.println("view count event published successfully" + result.getRecordMetadata().toString());
                   } else {
                       System.out.println("Error publishing event" + error.getMessage());
                   }
                });
    }
}
