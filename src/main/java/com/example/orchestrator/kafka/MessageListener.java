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
    private final String sendHamster = "SendHamster";
    private final String sendReviews = "sendReviews";

    private final String sendOrdersDataFromDB = "sendOrdersDataFromDB";

    public MessageListener() {
    }

    public String listenerGetAllProductsResponse() {
        log.info("Sent request to Database - get products");
        messageProducer.sendMessage("get all products", "getAllProductsDB");


        String products = null;
        Consumer<String, String> consumer = (Consumer<String, String>) factoryString.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singleton(topicProducts));


        ConsumerRecords<String, String> productsRecords = consumer.poll(10000);
        for (ConsumerRecord<String, String> record : productsRecords) {
            products = record.value();
        }
        consumer.close();
        return products;
    }

    //   для получения ордеров из бд
    public String listenerGetAllOrdersResponse() {
        log.info("Sent request to Database - get orders");
       messageProducer.sendMessage("get all orders", "GetAllOrders");

        String orders = null;
        Consumer<String, String> consumer = (Consumer<String, String>) factoryString.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singleton(sendHamster));
                ConsumerRecords<String, String> ordersRecords = consumer.poll(10000);
        for (ConsumerRecord<String, String> record : ordersRecords) {
            orders = record.value();
        }
        consumer.close();
        return orders;
    }

    public String listenerGetAllReviewsResponse() {
        log.info("Sent request to Database - get reviews");
        messageProducer.sendMessage("get all reviews", "GetAllReviews");
        String reviews = null;
        Consumer<String, String> consumer = (Consumer<String, String>) factoryString.getConsumerFactory().createConsumer();
        consumer.subscribe(Collections.singleton(sendReviews));
        ConsumerRecords<String, String> ordersRecords = consumer.poll(10000);
        for (ConsumerRecord<String, String> record : ordersRecords) {
            reviews = record.value();
        }
        consumer.close();
        return reviews;
    }
}

