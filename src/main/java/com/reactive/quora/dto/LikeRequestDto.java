package com.reactive.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequestDto {

    @NotBlank
    private String targetId;

    @NotBlank
    private String targetType;

    @NotNull
    private boolean isLike;
}
