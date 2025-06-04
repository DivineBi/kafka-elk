package com.learn.kafka.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateResponse {
    private String base;
    private String date;
    private Map<String, Double> rates;

}
