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
    @KafkaListener(topics = "test-topic1", containerFactory = "kafkaListenerContainerFactory")
    //Добавить файл на вход
    public void listenerTestTopic(String test) {
        log.info("Listener Test: string from Front {}", test);
    }
    @GetMapping("/test")
    public void Test(){
        messageProducer.sendMessage("product", "testTopic2");
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
    @KafkaListener(topics = "frontSaveOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveOrder(String order) {
        log.info("Get request from Front 'save order'");
        messageProducer.sendMessage(order, "saveOrderDB");// направляем запрос в базу
        log.info("Redirect request to Database 'save order' order = {}", order);
    }

    @KafkaListener(topics = "frontGetOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetOrder(String id) {
        log.info("Get request from Front 'get order'");
        messageProducer.sendMessage(id, "getOrderFromDB");// направляем запрос в базу
        log.info("Redirect request to Database 'get order' with id = {}", id);
    }

    @KafkaListener(topics = "frontDeleteOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerDeleteOrder(String id) {
        log.info("Get request from Front 'delete order'");
        messageProducer.sendMessage(id, "deleteProductDB");// направляем запрос в базу
        log.info("Redirect request to Database 'delete product' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateOrder(String id) {
        log.info("Get request from Front 'update order'");
        messageProducer.sendMessage(id, "updateOrderDB");// направляем запрос в базу
        log.info("Redirect request to Database 'update order' with id = {}", id);
    }
    @KafkaListener(topics = "frontSaveUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveUser(String user) {
        log.info("Get request from Front 'save user'");
        messageProducer.sendMessage(user, "saveUserDB");// направляем запрос в базу
        log.info("Redirect request to Database 'save user' user = {}", user);
    }

    @KafkaListener(topics = "frontGetUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetUser(String id) {
        log.info("Get request from Front 'get user'");
        messageProducer.sendMessage(id, "getUserFromDB");// направляем запрос в базу
        log.info("Redirect request to Database 'get user' with id = {}", id);
    }

    @KafkaListener(topics = "frontDeleteUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerDeleteUser(String id) {
        log.info("Get request from Front 'delete user'");
        messageProducer.sendMessage(id, "deleteUserDB");// направляем запрос в базу
        log.info("Redirect request to Database 'delete user' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateUser(String id) {
        log.info("Get request from Front 'update user'");
        messageProducer.sendMessage(id, "updateUserDB");// направляем запрос в базу
        log.info("Redirect request to Database 'update order' with id = {}", id);
    }



}
