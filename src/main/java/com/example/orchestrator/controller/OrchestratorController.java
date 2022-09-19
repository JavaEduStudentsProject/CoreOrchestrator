package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class OrchestratorController {

    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;

    @Autowired
    public OrchestratorController(MessageProducer messageProducer, MessageProducerFile messageProducerFile) {
        this.messageProducer = messageProducer;
        this.messageProducerFile = messageProducerFile;
    }

    @KafkaListener(topics = "test-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listenerReact(String str) throws IOException, InterruptedException {
        log.info("Listener orchestrator: from React,  str " + str);
//          messageProducer.sendMessage(product, "parser");
    }

    // Получаю файл от парсера и отправляю в базу
    @KafkaListener(topics = "parser", containerFactory = "kafkaListenerContainerFactory")
    public void listener(String product) throws IOException, InterruptedException {
        log.info("Listener orchestrator: from parser String, parser " + product);
          messageProducer.sendMessage(product, "SaveHamsters");
    }
    @GetMapping()
    @KafkaListener(topics = "topicFrontToParser", containerFactory = "kafkaListenerContainerFactory")
    public void listener() {
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\Magazin\\Parser\\src\\main\\resources\\file.csv");
        log.info("Listener orchestrator: file from Front {}", file.getName());
        messageProducerFile.sendMessage(file, "topicFrontToParser");
        log.info("Producer orchestrator: file {} to Parser, topicFrontToParser", file.getName());
    }

    // Гет продукт от фронта в базу
    @KafkaListener(topics = "GetHamster", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProduct(String id){
    log.info("Get request from Front 'get product'");
    messageProducer.sendMessage(id, "GetProduct");// направляем запрос в базу
    log.info("Redirect request to Database 'get product' with id = {}", id);
    }

    // Получаю продукт от базы
    @KafkaListener(topics = "SendHamster", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProductResponse(String product){
        log.info("Get response to a request from Database 'get product'");
        messageProducer.sendMessage(product, "SendProduct");// направляю продукт на фронт
        log.info("Send response to front 'get product' = {}", product);
    }

}
