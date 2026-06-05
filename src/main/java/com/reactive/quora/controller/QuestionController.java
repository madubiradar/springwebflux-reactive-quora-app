package com.reactive.quora.controller;

import com.reactive.quora.dto.QuestionRequestionDto;
import com.reactive.quora.dto.QuestionResponseDto;
import com.reactive.quora.models.Question;
import com.reactive.quora.models.QuestionElasticDocument;
import com.reactive.quora.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @GetMapping("/{id}")
    public Mono<QuestionResponseDto> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id)
                .doOnSuccess(response -> System.out.println("question found successfully" + response))
                .doOnError(error -> System.out.println("question found failed" + error.getMessage()));
    }
    @GetMapping
    public Flux<QuestionResponseDto> searchQuestions(
            @RequestParam String query,
            @RequestParam int offset,
            @RequestParam int page
    ) {
        return questionService.searchQuestions(query, offset, page);
    }


    @GetMapping("/elasticsearch")
    public List<QuestionElasticDocument> searchQuestionByElasticSearch(@RequestParam String query){
        return questionService.searchQuestionByElasticSearch(query);
    }
}
