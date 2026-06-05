package com.reactive.quora.service;


import com.reactive.quora.dto.AnswerRequestDto;
import com.reactive.quora.dto.AnswerResponseDto;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    public Mono<AnswerResponseDto> createAnswer(AnswerRequestDto answerRequestDto);

    Mono<AnswerResponseDto> getAnswerById(String id);
}
