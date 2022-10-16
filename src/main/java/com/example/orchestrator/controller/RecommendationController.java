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
    public String imitationOfKafkaRequestFromReact(@PathVariable("userId") String userId) {
        log.info("Get request from front, userId: " + userId);
        messageProducer.sendMessage(userId, "requestForUser");
        log.info("Redirect request to Py module");
        return "Answer from orchestrator";
    }

    @KafkaListener(topics = "requestOrdersDataFromOrchestrator", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void requestOrdersDataFromDB(ConsumerRecord<String, String> record) {
        String data = record.value();
        log.info("Get request from Python module: " + data);
        messageProducer.sendMessage(data, "requestOrdersDataFromDB");
        log.info("Redirect request to Database 'get all orders'");
    }

    @KafkaListener(topics = "sendOrdersDataFromDB", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getOrdersDataFromDB(ConsumerRecord<String, String> record) {
        log.info("Get message from database module: " + record.value());
        messageProducer.sendMessage(record.value(), "sendOrdersDataToRecommendationModule");
        log.info("Redirect request to Py module with orders data from DB");
    }

    @KafkaListener(topics = "sendRecommendedProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getRecommendedProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo здесь метод отправки данных в React
        messageProducer.sendMessage(record.value(), "dataForRecommendationComponent");
        log.info("Redirect request to React with recommended products data");
    }

    @KafkaListener(topics = "requestProductsDataFromOrchestrator", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void requestProductsDataFromDB(ConsumerRecord<String, String> record) {
        String data = record.value();
        log.info("Get request from Python module: " + data);
        messageProducer.sendMessage(data, "requestProductsDataFromDB");
        log.info("Redirect request to Database 'get all products'");
    }

    @KafkaListener(topics = "sendProductsDataFromDB", containerFactory = "kafkaListenerContainerFactory")
    public void getProductsDataFromDB(ConsumerRecord<String, String> record) {
        log.info("Get message from database module: " + record.value());
        messageProducer.sendMessage(record.value(), "sendProductsDataToRecommendationModule");
        log.info("Redirect request to Py module with products data from DB");
    }

    @KafkaListener(topics = "sendRecommendedCategoryProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getRecommendedCategoryProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo здесь метод отправки данных в React
        messageProducer.sendMessage(record.value(), "categoryDataForRecommendationComponent");
        log.info("Redirect request to React with recommended products data (categories)");
    }

    @CrossOrigin
    @GetMapping("/basket_request_from_react/{productsInBasketArray}")
    public String initBasketRequestFromReact(@PathVariable("productsInBasketArray") String productsInBasketArray) {
        log.info("Get request from front, userId: " + productsInBasketArray);
        messageProducer.sendMessage(productsInBasketArray, "requestForUserBasket");
        log.info("Redirect basket request to Py module");
        return "Answer from orchestrator";
    }

    @KafkaListener(topics = "requestProductsAndOrdersDataFromOrchestrator", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void requestProductsAndOrdersDataFromDB(ConsumerRecord<String, String> record) {
        String data = record.value();
        log.info("Get request from Python module: " + data);
        messageProducer.sendMessage(data, "requestProductsAndOrdersDataFromDB");
        log.info("Redirect request to Database 'get all products and orders'");
    }

    @KafkaListener(topics = "sendBasketRecommendedProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void getRecommendedBasketProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo здесь метод отправки данных в React
        messageProducer.sendMessage(record.value(), "basketDataForRecommendationComponent");
        log.info("Redirect request to React with recommended products data (basket)");
    }
}