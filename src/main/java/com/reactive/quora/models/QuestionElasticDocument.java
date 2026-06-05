package com.reactive.quora.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "questions")
@Builder
public class QuestionElasticDocument {

    @Id
    private String id;
    private String title;
    private String content;
}
