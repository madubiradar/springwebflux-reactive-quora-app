package com.reactive.quora.repositories;

import com.reactive.quora.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {

    Flux<Question> findByAuthorId(String authorId);

    Mono<Long> countByAuthorId(String authorId);
}
