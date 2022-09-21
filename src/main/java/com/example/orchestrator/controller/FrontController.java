package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/api")
public class FrontController {
    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;

    @Autowired
    public FrontController(MessageProducer messageProducer, MessageProducerFile messageProducerFile) {
        this.messageProducer = messageProducer;
        this.messageProducerFile = messageProducerFile;
    }

    @GetMapping()
    @KafkaListener(topics = "parseFileFront", containerFactory = "kafkaListenerContainerFactory")
    //Добавить файл на вход
    public void listenerParseFile() {
        File file = new File("CoreOrchestrator/file.csv");
        log.info("Listener orchestrator: file from Front {}", file.getName());
        messageProducerFile.sendMessage(file, "parseFileParser");
        log.info("Producer orchestrator: file {} to Parser, parseFileParser", file.getName());
    }

    @GetMapping("/get")
    // Гет продукт от фронта в базу//
    @KafkaListener(topics = "frontGetProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProduct() {
        String id = "728";
        log.info("Get request from Front 'get product'");
        messageProducer.sendMessage(id, "getProductFromDB");// направляем запрос в базу
        log.info("Redirect request to Database 'get product' with id = {}", id);
    }

    //этого метода пока нет в бд
//    @KafkaListener(topics = "frontGetAllProducts", containerFactory = "kafkaListenerContainerFactory")
//    public void listenerGetAllProducts(String products) {
//        log.info("Get request from Front 'get all products'");
//        messageProducer.sendMessage(products, "getAllProductsDB");// направляем запрос в базу
//        log.info("Redirect request to Database 'get product' with id = {}", products);
//    }
    @KafkaListener(topics = "frontSaveProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveProduct(String product) {
        log.info("Get request from Front 'save product'");
        messageProducer.sendMessage(product, "saveProductDB");// направляем запрос в базу
        log.info("Redirect request to Database 'save product' with id = {}", product);
    }

    @KafkaListener(topics = "frontDeleteProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerDeleteProduct(String id) {
        log.info("Get request from Front 'delete product'");
        messageProducer.sendMessage(id, "deleteProductDB");// направляем запрос в базу
        log.info("Redirect request to Database 'delete product' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateProduct(String id) {
        log.info("Get request from Front 'update product'");
        messageProducer.sendMessage(id, "updateProductDB");// направляем запрос в базу
        log.info("Redirect request to Database 'update product' with id = {}", id);
    }

}
