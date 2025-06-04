package com.learn.kafka.controller;

import com.learn.kafka.dto.ExchangeRateResponse;
import com.learn.kafka.producer.MessageProducer;
import com.learn.kafka.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/{base}")
    public Mono<ResponseEntity<ExchangeRateResponse>> getRates(@PathVariable String base) {
        return exchangeRateService.getExchangeRates(base)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
