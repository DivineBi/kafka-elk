package com.learn.kafka.service;

import com.learn.kafka.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public void saveExchangeRate(ExchangeRate exchangeRate) {
        IndexOperations indexOps = elasticsearchOperations.indexOps(ExchangeRate.class);
        indexOps.putMapping();

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(exchangeRate.getId())
                .withObject(exchangeRate)
                .build();

        elasticsearchOperations.index(indexQuery, indexOps.getIndexCoordinates());
        System.out.println(" Données enregistrées dans Elasticsearch !");
    }
}

