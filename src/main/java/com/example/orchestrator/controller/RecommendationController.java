package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Slf4j
@RestController
@Service
public class RecommendationController {
    MessageProducer messageProducer;

    @Autowired
    public RecommendationController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }
    //Request from front for recommended products for current user
    //todo change to kafka method
    @CrossOrigin
    @GetMapping("/request_from_react/{userId}")
    public void imitationOfKafkaRequestFromReact(@PathVariable("userId") String userId) {
        log.info("Get request from front, userId: " + userId);
        messageProducer.sendMessage(userId, "requestForUser");
        log.info("Redirect request to Py module");
    }

//    @KafkaListener(topics = "requestOrdersDataFromOrchestrator", groupId = "ordersDataGroup")
    @KafkaListener(topics = "requestOrdersDataFromOrchestrator", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void requestOrdersDataFromDB(ConsumerRecord<String, String> record) {
        String data = record.value();
        log.info("Get request from Python module: " + data);
//        String data = RepositoryImitation.ordersData;
        messageProducer.sendMessage(data, "requestOrdersDataFromDB");
        log.info("Redirect request to Database 'get all orders'");
    }

    @KafkaListener(topics = "sendOrdersDataFromDB", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getOrdersDataFromDB(ConsumerRecord<String, String> record) {
        log.info("Get message from database module: " + record.value());
        messageProducer.sendMessage(record.value(), "sendOrdersDataToRecommendationModule");
        log.info("Redirect request to Py module with data from DB");
    }

    @KafkaListener(topics = "sendRecommendedProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getRecommendedProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo здесь метод отправки данных в React
        messageProducer.sendMessage(record.value(), "dataForRecommendationComponent");
        log.info("Redirect request to React with recommended products data");
    }

}
