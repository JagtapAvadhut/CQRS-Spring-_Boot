package com.cqrs.command.controller;

import com.cqrs.command.entities.Product;
import com.cqrs.command.kafka.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/produce-data")
    public ResponseEntity<Object> produceData(@RequestParam Product product) {
        KafkaAdmin kafkaAdmin = kafkaTemplate.getKafkaAdmin();
        LOGGER.info("kafka-Admin {}", kafkaAdmin);
        CompletableFuture<SendResult<String, Object>> resultCompletableFuture = kafkaTemplate.send(KafkaConfig.PRODUCT_SAVE, product);
        LOGGER.info("send kafka data {}", resultCompletableFuture);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
