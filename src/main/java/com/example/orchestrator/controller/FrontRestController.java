package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import com.example.orchestrator.kafka.MessageProducerFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class FrontRestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping(value="/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(String str) throws ExecutionException, InterruptedException {
        kafkaTemplate.send("test-topic1", str).get();
    }

    @MessageMapping("sendMessage")
    @SendTo("/topic/group")
    public String getAll(@Payload String str){
        return str;
    }
}
