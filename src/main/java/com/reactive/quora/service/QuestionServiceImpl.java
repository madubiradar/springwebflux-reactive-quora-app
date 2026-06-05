package com.reactive.quora.service;

import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import com.reactive.quora.events.ViewCountEvent;
import com.reactive.quora.models.Question;
import com.reactive.quora.producer.KafkaEventProducer;
import com.reactive.quora.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository  questionRepository;

    private final KafkaEventProducer kafkaEventProducer;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, KafkaEventProducer kafkaEventProducer) {
        this.questionRepository = questionRepository;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Override
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestionDto questionRequestionDto) {
        Question question = Question.builder()
                .title(questionRequestionDto.getTitle())
                .content(questionRequestionDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return questionRepository.save(question)
                .map(this::toQuestionResponseDto)
                .doOnSuccess(response -> System.out.println("question created successfully" + response))
                .doOnError(error -> System.out.println("question creation failed" + error.getMessage()));
    }

    @Override
    public Flux<QuestionResponseDto> searchQuestions(String searchTerm, int offset, int page) {
        Pageable pageable = PageRequest.of(offset, page);

        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, pageable)
                .map(this::toQuestionResponseDto)
                .doOnError(error -> System.out.println("search questions failed" + error.getMessage()))
                .doOnComplete(() -> System.out.println("search questions successfully"));
    }

    @Override
    public Mono<QuestionResponseDto> getQuestionById(String id) {
        return questionRepository.findById(id)
                .map(this::toQuestionResponseDto)
                .doOnError(error -> System.out.println("get question by id failed" + error.getMessage()))
                .doOnSuccess(response -> {
                            System.out.println("get question by id successfully");
                            ViewCountEvent viewCountEvent = ViewCountEvent.builder()
                                    .targetId(id)
                                    .targetType("question")
                                    .timestamp(LocalDateTime.now())
                                    .build();
                            kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                        }
                );
    }

    private QuestionResponseDto toQuestionResponseDto(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .title(question.getTitle())
                .createdDate(question.getCreatedAt())
                .build();
    }
}
