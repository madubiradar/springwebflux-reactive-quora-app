package com.reactive.quora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponseDto {

    private String id;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
