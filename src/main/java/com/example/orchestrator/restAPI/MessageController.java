package com.example.orchestrator.restAPI;


import com.example.orchestrator.kafka.MessageListener;
import com.example.orchestrator.kafka.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping()
public class MessageController {

    private MessageProducer messageProducer;
    private MessageListener messageListener;

    public MessageController(MessageProducer messageProducer, MessageListener messageListener) {
        this.messageProducer = messageProducer;
        this.messageListener = messageListener;
    }

    @GetMapping("")
    public ResponseEntity<String> getAll() {
        messageProducer.sendMessage("getAllProducts", "frontGetAllProducts");
        return ResponseEntity.ok("/temp_props_1.json");
    }

    @PostMapping("")
    @KafkaListener(topics = "sendAllProductsToFront", containerFactory = "kafkaListenerContainerFactory")
    public String listener(String products) throws IOException, InterruptedException {
        log.info("Listener orchestrator: from parser String, parser " + products);
        return products;
    }

//    @GetMapping()
//    public

}
