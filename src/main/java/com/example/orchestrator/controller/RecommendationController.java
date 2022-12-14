package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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
    public List imitationOfKafkaRequestFromReact(@PathVariable("userId") String userId) {
        log.info("Get request from front, userId: " + userId);
        messageProducer.sendMessage(userId, "requestForUser");
        messageProducer.sendMessage(userId, "syncRequestForUser");
        log.info("Redirect request to Py module");
        return Arrays.asList("{'Orchestrator answer': 'got your request'}");
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

    @CrossOrigin
    @GetMapping("/basket_request_from_react/{productsInBasketArray}")
    public List initBasketRequestFromReact(@PathVariable("productsInBasketArray") String productsInBasketArray) {
        log.info("Get request from front, product's id array: " + productsInBasketArray);
        messageProducer.sendMessage(productsInBasketArray, "requestForUserBasket");
        log.info("Redirect basket request to Py module (first topic");
        messageProducer.sendMessage(productsInBasketArray, "syncRequestForUserBasket");
        log.info("Redirect basket request to Py module (second topic)");
        return Arrays.asList("{'Orchestrator answer': 'got your request'}");
    }

    @KafkaListener(topics = "requestProductsAndOrdersDataFromOrchestrator", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void requestProductsAndOrdersDataFromDB(ConsumerRecord<String, String> record) {
        String data = record.value();
        log.info("Get request from Python module: " + data);
        messageProducer.sendMessage(data, "requestProductsAndOrdersDataFromDB");
        log.info("Redirect request to Database 'get all products and orders'");
    }

    @KafkaListener(topics = "sendRecommendedProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void sendRecommendedProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo ?????????? ?????????? ???????????????? ???????????? ?? React
//        messageProducer.sendMessage(record.value(), "dataForRecommendationComponent");
        messageProducer.sendMessageToWebSocket("/topic/cosineSimData", record.value());
        log.info("Redirect request to React with recommended products data (cosine similarity)");
    }

    @KafkaListener(topics = "sendRecommendedBestProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void sendRecommendedBestProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo ?????????? ?????????? ???????????????? ???????????? ?? React
//        messageProducer.sendMessage(record.value(), "categoryDataForRecommendationComponent");
        messageProducer.sendMessageToWebSocket("/topic/bestProductData", record.value());
        log.info("Redirect request to React with recommended best products data");
    }

    @KafkaListener(topics = "sendBasketRecommendedProductsData", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void sendRecommendedBasketProductsData(ConsumerRecord<String, String> record) {
        log.info("Get message from Py module: " + record.value());
        //todo ?????????? ?????????? ???????????????? ???????????? ?? React
//        messageProducer.sendMessage(record.value(), "basketDataForRecommendationComponent");
        messageProducer.sendMessageToWebSocket("/topic/basketCategoriesData", record.value());
        log.info("Redirect request to React with recommended products data (basket)");
    }

    @CrossOrigin
    @MessageMapping("/sendRecommendedProductsData")
    @SendTo("/topic/cosineSimData")
    public Message broadcastCosineSimMessage(@Payload Message message) {
        return message;
    }

    @CrossOrigin
    @MessageMapping("/sendRecommendedBestProductsData")
    @SendTo("/topic/bestProductData")
    public Message broadcastBestProductMessage(@Payload Message message) {
        return message;
    }

    @CrossOrigin
    @MessageMapping("/sendBasketRecommendedProductsData")
    @SendTo("/topic/basketCategoriesData")
    public Message broadcastBasketCategoriesMessage(@Payload Message message) {
        return message;
    }
}
