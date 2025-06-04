package com.learn.kafka;

import com.learn.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private MessageProducer messageProducer;

    @Value("${app.kafka.topic}")
    private String topic;

    @PostMapping("/produce")
    public ResponseEntity<String> sendMessage(@RequestParam("content") String content) {
        messageProducer.sendMessage(topic, content);
        return ResponseEntity.ok(content);
    }

}