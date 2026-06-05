package com.reactive.quora.service;

import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import com.reactive.quora.events.ViewCountEvent;
import com.reactive.quora.models.Question;
import com.reactive.quora.models.QuestionElasticDocument;
import com.reactive.quora.producer.KafkaEventProducer;
import com.reactive.quora.repositories.QuestionElasticDocumentRepository;
import com.reactive.quora.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;

    private final KafkaEventProducer kafkaEventProducer;

    private final IQuestionIndexService questionIndexService;

    private final QuestionElasticDocumentRepository elasticDocumentRepository;

    private final ElasticsearchOperations elasticsearchOperations; // Inject ElasticsearchOperations

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, KafkaEventProducer kafkaEventProducer, IQuestionIndexService questionIndexService, QuestionElasticDocumentRepository elasticDocumentRepository, ElasticsearchOperations elasticsearchOperations) {
        this.questionRepository = questionRepository;
        this.kafkaEventProducer = kafkaEventProducer;
        this.questionIndexService = questionIndexService;
        this.elasticDocumentRepository = elasticDocumentRepository;
        this.elasticsearchOperations = elasticsearchOperations; // Initialize
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
                .map(savedQuestion -> {
                    questionIndexService.createQuestionIndex(savedQuestion); // dumping the question to elasticsearch
                    return toQuestionResponseDto(savedQuestion);
                })
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));
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

    @Override
    public List<QuestionElasticDocument> searchQuestionByElasticSearch(String query) {
        // Build the multi-match query using the new Elasticsearch Java Client's Query DSL
        Query multiMatchElasticQuery = Query.of(q -> q
                .multiMatch(mm -> mm
                        .query(query)
                        .fields("title", "content")
                )
        );

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(multiMatchElasticQuery)
                .build();

        return elasticsearchOperations.search(searchQuery, QuestionElasticDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    private QuestionResponseDto toQuestionResponseDto(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .title(question.getTitle()) // This line seems to be a duplicate, keeping it for now to match original code
                .createdDate(question.getCreatedAt())
                .build();
    }

}
