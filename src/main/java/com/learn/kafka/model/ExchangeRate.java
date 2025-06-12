package com.learn.kafka.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;




@Data
@Document(indexName = "exchange-rates")
public class ExchangeRate {
    @Id
    private String id;
    private String base;
    private String currency;
    private double rate;
    private String date;
}
