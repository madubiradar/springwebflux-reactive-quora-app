package com.reactive.quora.service;

import com.reactive.quora.models.Question;
import com.reactive.quora.models.QuestionElasticDocument;
import com.reactive.quora.repositories.QuestionElasticDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionIndexServiceImpl implements IQuestionIndexService{

    private final QuestionElasticDocumentRepository elasticDocumentRepository;

    @Autowired
    public QuestionIndexServiceImpl(QuestionElasticDocumentRepository elasticDocumentRepository) {
        this.elasticDocumentRepository = elasticDocumentRepository;
    }

    @Override
    public void createQuestionIndex(Question question) {
        QuestionElasticDocument elasticDocument = QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();
        elasticDocumentRepository.save(elasticDocument);
    }
}
