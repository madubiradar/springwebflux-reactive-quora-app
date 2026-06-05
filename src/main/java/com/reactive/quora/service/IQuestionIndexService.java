package com.reactive.quora.service;

import com.reactive.quora.models.Question;
import org.springframework.stereotype.Service;

@Service
public interface IQuestionIndexService {

    void createQuestionIndex(Question question);
}
