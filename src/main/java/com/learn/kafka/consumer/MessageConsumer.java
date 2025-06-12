package com.learn.kafka.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.dto.ExchangeRateResponse;
import com.learn.kafka.model.ExchangeRate;
import com.learn.kafka.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;
    private final ElasticsearchService elasticsearchService;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    //public void listen(String message) {
    //    log.info("Message receive : {}", message);
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        log.info("Received message from Kafka: {}", message);

        try {
            ExchangeRateResponse response = objectMapper.readValue(message, ExchangeRateResponse.class);

            String base = response.getBase();
            String date = response.getDate();

            for (Map.Entry<String, Double> entry : response.getRates().entrySet()) {
                String currency = entry.getKey();
                double rate = entry.getValue();

                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setBase(base);
                exchangeRate.setCurrency(currency);
                exchangeRate.setRate(rate);
                exchangeRate.setDate(date);
                exchangeRate.setId(base + "-" + currency + "-" + date); // ID unique pour Elasticsearch

                elasticsearchService.saveExchangeRate(exchangeRate);
                log.info("Saved exchange rate to Elasticsearch: {}", exchangeRate);
            }

        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize Kafka message", e);
        }


    }

}