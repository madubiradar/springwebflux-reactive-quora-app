package com.reactive.quora.service;


import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import com.reactive.quora.models.Question;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDto> createQuestion(QuestionRequestionDto question);
}
