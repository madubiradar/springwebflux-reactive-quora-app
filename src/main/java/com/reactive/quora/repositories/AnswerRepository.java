package com.reactive.quora.repositories;

import com.reactive.quora.models.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AnswerRepository extends ReactiveMongoRepository<Question, String> {

}
