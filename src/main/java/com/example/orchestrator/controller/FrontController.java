package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageListener;
import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import com.example.orchestrator.kafka.MessageProducerReview;
import com.example.orchestrator.model.JsonReview;
import com.example.orchestrator.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;

@Slf4j
@RestController
@RequestMapping("/api")
@Service
@CrossOrigin
public class FrontController {
    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;
    MessageProducerReview messageProducerReview;
    @Autowired
    MessageListener ml;

    @Autowired
    public FrontController(MessageProducer messageProducer, MessageProducerFile messageProducerFile, MessageProducerReview messageProducerReview) {
        this.messageProducer = messageProducer;
        this.messageProducerFile = messageProducerFile;
        this.messageProducerReview = messageProducerReview;
    }

    @GetMapping()
    @KafkaListener(topics = "parseFileFront", containerFactory = "kafkaListenerContainerFactory")
    public void listenerParseFile() {
        File file = new File("CoreOrchestrator/products.json");

        log.info("Listener orchestrator: file from Front {}", file.getName());
        messageProducerFile.sendMessage(file, "parseFileParser");
        log.info("Producer orchestrator: file {} to Parser, parseFileParser", file.getName());
    }

    @GetMapping("/get")
    @KafkaListener(topics = "frontGetProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProduct() {
        String id = "728";
        log.info("Get request from Front 'get product'");
        messageProducer.sendMessage(id, "getProductFromDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'get product' with id = {}", id);
    }

    @GetMapping("/products")
    public String getAllProductsFromDB() {
        String products = ml.listenerGetAllProductsResponse();
        log.info("Products from Database: {}", products);
        return products;
    }

    @GetMapping("/orders")
    public String getAllOrdersFromDB() {
        String orders = ml.listenerGetAllOrdersResponse();
        log.info("Orders from Database: {}", orders);
        return orders;
    }

    @GetMapping("/reviews")
    public String getAllReviewsFromDB() {
        String reviews = ml.listenerGetAllReviewsResponse();
        log.info("Reviews from Database: {}", reviews);
        return reviews;
    }

    @PostMapping("/createOrder")
    public void saveOrderInDB(@RequestBody String order) {
        log.info("Get request from Front 'save order'");
        messageProducer.sendMessage(order, "saveOrderDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'save order' order = {}", order);
    }

    @PostMapping("/createRating")
    public void saveRating(@Valid @RequestBody String ratingRequest) {
        log.info("Get request from Front 'save rate'");
        messageProducer.sendMessage(ratingRequest, "saveRateDB");
        log.info("Redirect request to Database 'save rate' rate = {}", ratingRequest);
    }

    @KafkaListener(topics = "frontSaveProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveProduct(String product) {
        log.info("Get request from Front 'save product'");
        messageProducer.sendMessage(product, "saveProductDB");// ???????????????????? ???????????? ?? ????????
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
        messageProducer.sendMessage(id, "deleteProductDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'delete product' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateProduct", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateProduct(String id) {
        log.info("Get request from Front 'update product'");
        messageProducer.sendMessage(id, "updateProductDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'update product' with id = {}", id);
    }

    @KafkaListener(topics = "frontSaveOrders", containerFactory = "kafkaListenerContainerFactoryTwo")
    public void listenerSaveOrders(String orders) {
        log.info("Get request from Front 'save orders'");
        log.info("Orders: " + orders);
        log.info(orders.getClass().getName());
        messageProducer.sendMessage(orders, "SaveOrders");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'save orders'", orders);
    }

    @KafkaListener(topics = "frontGetOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetOrder(String id) {
        log.info("Get request from Front 'get order'");
        messageProducer.sendMessage(id, "getOrderFromDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'get order' with id = {}", id);
    }

    @KafkaListener(topics = "frontDeleteOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerDeleteOrder(String id) {
        log.info("Get request from Front 'delete order'");
        messageProducer.sendMessage(id, "deleteProductDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'delete product' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateOrder(String id) {
        log.info("Get request from Front 'update order'");
        messageProducer.sendMessage(id, "updateOrderDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'update order' with id = {}", id);
    }

    @KafkaListener(topics = "frontSaveUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerSaveUser(String user) {

        log.info("Get request from Front 'save user'");
        messageProducer.sendMessage(user, "saveUserDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'save user' user = {}", user);
    }

    @KafkaListener(topics = "frontGetUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetUser(String userName) {
        log.info("Get request from Front 'get user'");
        messageProducer.sendMessage(userName, "GetUser");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'get user' with id = {}", userName);
    }

    @KafkaListener(topics = "frontDeleteUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerDeleteUser(String id) {
        log.info("Get request from Front 'delete user'");
        messageProducer.sendMessage(id, "deleteUserDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'delete user' with id = {}", id);
    }

    @KafkaListener(topics = "frontUpdateUser", containerFactory = "kafkaListenerContainerFactory")
    public void listenerUpdateUser(String id) {
        log.info("Get request from Front 'update user'");
        messageProducer.sendMessage(id, "updateUserDB");// ???????????????????? ???????????? ?? ????????
        log.info("Redirect request to Database 'update order' with id = {}", id);
    }

    @PostMapping("/review")
    public ResponseEntity<?> saveReview(@Valid @RequestBody JsonReview review) {
        JsonReview newReview = new JsonReview(review.getProductId(),
                review.getRating(),
                review.getUserId(),
                review.getReview()
        );
        messageProducerReview.sendMessage(newReview, "SaveReview");
        log.info("The review {} was sent to Database for save", newReview.getReview());
        return ResponseEntity.ok(new MessageResponse("Thanks for your feedback!"));
    }

}