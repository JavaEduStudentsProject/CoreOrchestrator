package com.example.orchestrator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import java.util.Collections;


@Slf4j
public class MessageListener {
    @Autowired
    MessageProducer messageProducer;

    @Qualifier("kafkaListenerContainerFactory")
    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, String> factoryString;

    @Autowired
    KafkaConsumerConfig config;
    private final String topicProducts = "sendALlProducts";
    public MessageListener() {
    }

    public String listenerGetAllProductsResponse() {
        log.info("Get response to a request from Database 'get products'");
        messageProducer.sendMessage("get all products", "frontGetAllProducts");
        String products = null;
        Consumer<String, String> consumer = (Consumer<String, String>) factoryString.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singleton(topicProducts));

        ConsumerRecords<String, String> productsRecords = consumer.poll(10000);
        System.out.println(productsRecords);
        for (ConsumerRecord<String,String> record : productsRecords)
        {
            products = record.value();
            System.out.println(products);
        }

        consumer.close();
        return products;
    }
}

