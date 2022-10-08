package com.example.orchestrator.restAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "test-topic1", groupId = "testGroup")
    public void listen(String str){
        System.out.println("sending via kafka listener...");
        template.convertAndSend("/topics/group", str);
    }

//    private MessageProducer messageProducer;
//    private MessageListener messageListener;

//    @Autowired
//    private ReplyingKafkaTemplate<String, String, String> replyTemplate;
//
//    public MessageController(MessageProducer messageProducer, MessageListener messageListener) {
//        this.messageProducer = messageProducer;
//        this.messageListener = messageListener;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<String> getAll() {
//        messageProducer.sendMessage("getAllProducts", "frontGetAllProducts");
//
//        return ResponseEntity.ok("getting all prod");
//    }


}
