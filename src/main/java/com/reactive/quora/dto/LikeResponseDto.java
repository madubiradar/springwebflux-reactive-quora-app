package com.reactive.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponseDto {

    private String id;

    @NotBlank
    private String targetId;

    @NotBlank
    private String targetType;

    @NotNull
    private boolean isLike;

    private LocalDateTime createdAt;
}
