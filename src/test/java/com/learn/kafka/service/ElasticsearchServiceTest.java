package com.learn.kafka.service;

import com.learn.kafka.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@SpringBootTest
public class ElasticsearchServiceTest {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void testElasticsearchConnection() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setCurrency("EUR");
        exchangeRate.setRate(0.875);
        exchangeRate.setDate("2025-06-11");
        exchangeRate.setId("USD-EUR-2025-06-11");

        elasticsearchOperations.save(exchangeRate);
        System.out.println("✅ Données insérées dans Elasticsearch !");
    }
}
