package com.example.orchestrator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic parseFile() {
        log.info("Create orchestrator topic parseFile");
        return new NewTopic("parseFile", 1, (short) 1);
    }

    @Bean
    public NewTopic getProduct() {
        log.info("Create orchestrator topic getProduct");
        return new NewTopic("getProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic getAllProducts() {
        log.info("Create orchestrator topic getAllProducts");
        return new NewTopic("getAllProducts", 1, (short) 1);
    }

    @Bean
    public NewTopic saveProduct() {
        log.info("Create orchestrator topic saveProduct");
        return new NewTopic("saveProduct", 1, (short) 1);
    }
    @Bean
    public NewTopic save() {
        log.info("Create orchestrator topic save");
        return new NewTopic("save", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteProduct() {
        log.info("Create orchestrator topic deleteProduct");
        return new NewTopic("deleteProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic updateProduct() {
        log.info("Create orchestrator topic updateProduct");
        return new NewTopic("updateProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic sendProduct() {
        log.info("Create orchestrator topic sendProduct");
        return new NewTopic("sendProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic sendAllProduct() {
        log.info("Create orchestrator topic sendAllProduct");
        return new NewTopic("sendAllProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic topicReact() {
        return new NewTopic("test-topic", 2, (short) 1);
    }

    @Bean
    public NewTopic topicParser() {
        log.info("Create orchestrator topic parser");
        return new NewTopic("parser", 1, (short) 1);
    }
}
