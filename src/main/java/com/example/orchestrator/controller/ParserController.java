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
public class ParserController {

    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;

    @Autowired
    public ParserController(MessageProducer messageProducer, MessageProducerFile messageProducerFile) {
        this.messageProducer = messageProducer;
        this.messageProducerFile = messageProducerFile;
    }

//    @KafkaListener(topics = "test-topic", containerFactory = "kafkaListenerContainerFactory")
//    public void listenerReact(String str) throws IOException, InterruptedException {
//        log.info("Listener orchestrator: from React,  str " + str);
////          messageProducer.sendMessage(product, "parser");
//    }

    // Получаю файл от парсера и отправляю в базу
    @KafkaListener(topics = "parseFileParser", containerFactory = "kafkaListenerContainerFactory")
    public void listener(String product) throws IOException, InterruptedException {
        log.info("Listener orchestrator: from parser String, parser " + product);
//          messageProducer.sendMessage(product, "save");
          messageProducer.sendMessage("govno", "testFront");
    }




}
