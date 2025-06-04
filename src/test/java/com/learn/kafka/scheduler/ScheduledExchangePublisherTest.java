package com.learn.kafka.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.dto.ExchangeRateResponse;
import com.learn.kafka.producer.MessageProducer;
import com.learn.kafka.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledExchangePublisherTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private ObjectMapper objectMapper = new ObjectMapper();

    private ScheduledExchangePublisher publisher;

    @BeforeEach
    void setUp() {
        String topic = "exchange-rates"; // <--- FIX: injecte une valeur non-nulle
        publisher = new ScheduledExchangePublisher(exchangeRateService, messageProducer, objectMapper, topic);
    }

    @Test
    void testFetchAndSendExchangeRates() {
        // given
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setBase("USD");
        mockResponse.setRates(Map.of("EUR", 0.92));

        Mockito.when(exchangeRateService.getExchangeRates("USD"))
                .thenReturn(Mono.just(mockResponse));

        // Act
        publisher.publishExchangeRates().block();

        // Assert
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(messageProducer).sendMessage(topicCaptor.capture(), messageCaptor.capture());

        assertEquals("exchange-rates", topicCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("USD"));
    }
}
