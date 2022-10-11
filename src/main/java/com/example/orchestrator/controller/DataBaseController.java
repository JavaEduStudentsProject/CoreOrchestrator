package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Properties;

@Slf4j
@RestController
public class DataBaseController {
    MessageProducer messageProducer;


    private final String clientId = "myApplicationName";
    private final String groupId = "mygroupId";
    private final String endpoints = "localhost:9092";
    private final String topic = "SendUser";
    private final String autoOffsetResetPolicy = "earliest";
    private final String securityProtocol = "SASL_SSL";
    private final String securitySaslMechanism = "SCRAM-SHA-256";
    private final String keyDeserializer = ByteArrayDeserializer.class.getCanonicalName();
    private final String valueDeserializer = StringDeserializer.class.getCanonicalName();

    @Autowired
    public DataBaseController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }
    // Получаю продукт от базы
    @KafkaListener(topics = "sendProductFromDB", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetProductResponse(String product) {
        log.info("Get response to a request from Database 'get product'");
        messageProducer.sendMessage(product, "sendProductToFront");
        log.info("Send response to front 'get product' = {}", product);
    }
    //этого метода пока нет в бд
    @KafkaListener(topics = "sendALlProductsDB", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetAllProductsResponse(String products) {
        log.info("Get response to a request from Database 'get product'");
        messageProducer.sendMessage(products, "sendALlProductsToFront");
        log.info("Send response to front 'get product' = {}", products);
    }

    @KafkaListener(topics = "SendOrder", containerFactory = "kafkaListenerContainerFactory")
    public void listenerGetOrderResponse(String product) {
        log.info("Get response to a request from Database 'get order'");
        messageProducer.sendMessage(product, "sendOrderToFront");
        log.info("Send response to front 'get order' = {}", product);
    }
    @GetMapping("api/hello")
    @KafkaListener(topics = "SendUser", containerFactory = "kafkaListenerContainerFactory")
    public String listenerGetUserResponse(String user) {
        log.info("Get response to a request from Database 'get user'");
        messageProducer.sendMessage(user, "sendUserToFront");
        log.info("Send response to front 'get user' = {}", user);
        KafkaConsumer<byte[], String> consumer = new KafkaConsumer<>(getProperties());
        consumer.subscribe(Collections.singletonList(topic));
        consumer.poll(10000);
        return user;
    }

    private Properties getProperties() {
        Properties props = new Properties();
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, endpoints);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetPolicy);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
//        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
//        props.put(SaslConfigs.SASL_MECHANISM, securitySaslMechanism);
        return props;
    }

}
