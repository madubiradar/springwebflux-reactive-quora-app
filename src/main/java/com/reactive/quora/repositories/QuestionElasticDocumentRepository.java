package com.reactive.quora.repositories;

import com.reactive.quora.models.QuestionElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionElasticDocumentRepository extends ElasticsearchRepository<QuestionElasticDocument, String> {

}
