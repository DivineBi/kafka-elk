package com.learn.kafka.service;

import com.learn.kafka.dto.ExchangeRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {
    private final WebClient webClient;

        public ExchangeRateService(WebClient.Builder webClientBuilder) {
            this.webClient = webClientBuilder.baseUrl("https://api.exchangerate-api.com").build();
        }

        public Mono<ExchangeRateResponse> getExchangeRates(String baseCurrency) {
            return webClient
                    .get()
                    .uri("/v4/latest/{base}", baseCurrency)
                    .retrieve()
                    .bodyToMono(ExchangeRateResponse.class);
        }
}
