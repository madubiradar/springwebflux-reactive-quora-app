package com.reactive.quora.controller;

import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import com.reactive.quora.models.Question;
import com.reactive.quora.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private IQuestionService  questionService;

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }
    @PostMapping
    public Mono<QuestionResponseDto> saveQuestion(@RequestBody QuestionRequestionDto questionRequestionDto) {
        return questionService.createQuestion(questionRequestionDto)
                .doOnSuccess(response -> System.out.println("question created successfully" + response))
                .doOnError(error -> System.out.println("question creation failed" + error.getMessage()));
    }

}
