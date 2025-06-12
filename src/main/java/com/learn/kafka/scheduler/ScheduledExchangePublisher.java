package com.learn.kafka.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.producer.MessageProducer;
import com.learn.kafka.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class ScheduledExchangePublisher {

    private final ExchangeRateService exchangeRateService;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;
    private final String topic;


    public ScheduledExchangePublisher(
            ExchangeRateService exchangeRateService,
            MessageProducer messageProducer,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topic}") String topic
    ) {
        this.exchangeRateService = exchangeRateService;
        this.messageProducer = messageProducer;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    @Scheduled(fixedRate = 60000) // Exemple : exécution chaque 60 secondes
    public void fetchAndSendExchangeRates() {
        publishExchangeRates().subscribe(); // pour déclencher la chaîne réactive
    }

    public Mono<Void> publishExchangeRates() {
        return exchangeRateService.getExchangeRates("USD")
                .doOnNext(response -> log.info("Fetched exchange rates: {}", response))
                .flatMap(response -> {
                    try {
                        String json = objectMapper.writeValueAsString(response);
                        messageProducer.sendMessage(topic, json);
                        log.info("Message sent to topic {}: {}", topic, json);
                        return Mono.empty();
                    } catch (JsonProcessingException e) {
                        log.error("Error serializing exchange rate response", e);
                        return Mono.error(e);
                    }
                });

    }
}

