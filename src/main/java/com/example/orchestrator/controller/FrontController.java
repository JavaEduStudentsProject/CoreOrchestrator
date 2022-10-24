package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageListener;
import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/api")
@Service
@CrossOrigin
public class FrontController {
    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;
    @Autowired
    MessageListener ml;

    @Autowired
    public FrontController(MessageProducer messageProducer, MessageProducerFile messageProducerFile) {
        this.messageProducer = messageProducer;
        this.messageProducerFile = messageProducerFile;
    }

    @GetMapping()
    @KafkaListener(topics = "parseFileFront", containerFactory = "kafkaListenerContainerFactory")
    //Добавить файл на вход
    public void listenerParseFile() {
       // File file = new File("CoreOrchestrator/file.csv");
        File file = new File("CoreOrchestrator/products.json");

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

    @GetMapping("/products")
    public String getAllProductsFromDB (){
        String products = ml.listenerGetAllProductsResponse();
        log.info("Products from Database: {}", products);
        return products;
    }

    @PostMapping("/createOrder")
    public void saveOrderInDB (@RequestBody String order){
        log.info("Get request from Front 'save order'");
        messageProducer.sendMessage(order, "saveOrderDB");// направляем запрос в базу
        log.info("Redirect request to Database 'save order' order = {}", order);
    }

    @KafkaListener(topics = "frontSaveProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveProduct(String product) {
        log.info("Get request from Front 'save product'");
        messageProducer.sendMessage(product, "saveProductDB");// направляем запрос в базу
        log.info("Redirect request to Database 'save product' with id = {}", product);
    }

    @KafkaListener(topics = "frontSaveProducts", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void listenerSaveProducts(String products) {
        log.info("Get request from Front 'save products'");
        log.info("Products: " + products);
        messageProducer.sendMessage(products, "save");
        messageProducer.sendMessage(products, "SaveHamsters");
        log.info("Redirect request to Database 'save products'", products);
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
//    @KafkaListener(topics = "frontSaveOrder", containerFactory = "kafkaListenerContainerFactory")
//    public void listenerSaveOrder(String order) {
//        log.info("Get request from Front 'save order'");
//        messageProducer.sendMessage(order, "saveOrderDB");// направляем запрос в базу
//        log.info("Redirect request to Database 'save order' order = {}", order);
//    }

    @KafkaListener(topics = "frontSaveOrders", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void listenerSaveOrders(String orders) {
        log.info("Get request from Front 'save orders'");
        log.info("Orders: " + orders);
        log.info(orders.getClass().getName());
        messageProducer.sendMessage(orders, "SaveOrders");// направляем запрос в базу
        log.info("Redirect request to Database 'save orders'", orders);
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
    public void listenerGetUser(String userName) {
        log.info("Get request from Front 'get user'");
        messageProducer.sendMessage(userName, "GetUser");// направляем запрос в базу
        log.info("Redirect request to Database 'get user' with id = {}", userName);
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