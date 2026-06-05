package com.reactive.quora.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "likes")
public class Like {

    @Id
    private String id;

    @NotBlank
    private String targetId;

    @NotBlank
    private String targetType; //convert to enum

    @NotNull
    private boolean isLike;

    @CreatedDate
    private LocalDateTime createdAt;
}
