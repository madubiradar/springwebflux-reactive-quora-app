package com.reactive.quora.service;


import com.reactive.quora.dto.AnswerRequestDto;
import com.reactive.quora.dto.AnswerResponseDto;
import com.reactive.quora.dto.LikeRequestDto;
import com.reactive.quora.dto.LikeResponseDto;
import reactor.core.publisher.Mono;

public interface ILikeService {

    Mono<LikeResponseDto> createLike(LikeRequestDto likeRequestDto);
    Mono<LikeResponseDto> getLikeByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDto> getDisLikeByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDto> toggleLike(String targetId, String targetType, boolean isLike);
}
