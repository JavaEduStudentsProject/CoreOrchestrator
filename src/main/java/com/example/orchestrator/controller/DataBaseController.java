package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DataBaseController {

    MessageProducer messageProducer;

    @Autowired
    public DataBaseController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;

    }


    // Получаю продукт от базы
    @KafkaListener(topics = "sendProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProductResponse(String product) {
        log.info("Get response to a request from Database 'get product'");
        messageProducer.sendMessage(product, "sendProduct");// направляю продукт на фронт
        log.info("Send response to front 'get product' = {}", product);
    }
    //этого метода пока нет в бд
//    @KafkaListener(topics = "sendALlProducts", containerFactory = "kafkaListenerContainerFactory")
//    public void listenerGetAllProductsResponse(String products) {
//        log.info("Get response to a request from Database 'get product'");
//        messageProducer.sendMessage(products, "sendALlProducts");// направляю продукт на фронт
//        log.info("Send response to front 'get product' = {}", products);
//    }



}
