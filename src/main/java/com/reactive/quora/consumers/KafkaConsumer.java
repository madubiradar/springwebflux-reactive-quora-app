package com.reactive.quora.consumers;

import com.reactive.quora.events.ViewCountEvent;
import com.reactive.quora.repositories.QuestionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final QuestionRepository questionRepository;

    public KafkaConsumer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @KafkaListener(
            topics = com.reactive.quora.config.KafkaConfig.TOPIC_NAME,
            groupId = "${spring.kafka.consumer.group-id:view-count-consumer}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    System.out.println("Incrementing view count for question: " + question.getId());
                    Integer views = question.getViewCount();
                    question.setViewCount(views == null ? 0 : views + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                    System.out.println("View count incremented for question: " + updatedQuestion.getId());
                }, error -> {
                    System.out.println("Error incrementing view count for question: " + error.getMessage());
                });
    }

}
