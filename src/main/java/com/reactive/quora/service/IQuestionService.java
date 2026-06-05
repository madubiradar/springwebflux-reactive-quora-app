package com.reactive.quora.service;


import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    Mono<QuestionResponseDto> createQuestion(QuestionRequestionDto question);
    Flux<QuestionResponseDto> searchQuestions(String searchTerm, int offset, int page);
    Mono<QuestionResponseDto> getQuestionById(String id);
}
