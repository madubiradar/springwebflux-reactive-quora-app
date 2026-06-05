package com.reactive.quora.repositories;

import com.reactive.quora.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends ReactiveMongoRepository<Question, String> {

}
