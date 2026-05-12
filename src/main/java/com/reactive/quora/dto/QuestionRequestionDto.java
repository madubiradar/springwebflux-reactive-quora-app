package com.reactive.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestionDto {


    @NotBlank(message = "Title required")
    @Size(max = 100, message = "Title must not be maximum 100 chars")
    private String title;

    @NotBlank
    @Size(max = 10000, message = "Content must be between 10 to 10000 characters")
    private String content;

    private String authorId;
}
