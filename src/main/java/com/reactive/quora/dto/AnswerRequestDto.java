package com.reactive.quora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRequestDto {

    @NotBlank(message = "Content is required")
    private String content;
    private String questionId;

}
