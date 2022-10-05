package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/front")
public class FrontRestController {
    MessageProducer messageProducer;
    MessageProducerFile messageProducerFile;


    @GetMapping
    @KafkaListener(topics = "sendParsedString", containerFactory = "kafkaListenerContainerFactory")
    public String listener(String product) throws IOException, InterruptedException {
        log.info("Listener orchestrator: from parser String {}", product);
//          messageProducer.sendMessage(product, "save");
//        messageProducer.sendMessage("govno", "testFront");
        return product;
    }

}
