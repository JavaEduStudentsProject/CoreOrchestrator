package com.example.orchestrator.kafka;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

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
    public NewTopic parseFileFront() {
        return new NewTopic("parseFileFront", 1, (short) 1);
    }

    //нужно ли создавать топик конфиги для продюсера?
//    @Bean
//    public NewTopic parseFileParser() {
//        log.info("Create orchestrator topic parseFileParser");
//        return new NewTopic("parseFileParser", 1, (short) 1);
//    }
//    @Bean
//    public NewTopic getProductFromDB() {
//        log.info("Create orchestrator topic getProductFromDB");
//        return new NewTopic("getProductFromDB", 1, (short) 1);
//    }
    @Bean
    public NewTopic frontGetProduct() {
        return new NewTopic("frontGetProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic frontGetAllProducts() {
        return new NewTopic("frontGetAllProducts", 1, (short) 1);
    }

    @Bean
    public NewTopic sendALlProductsDB() {
        return new NewTopic("sendALlProductsDB", 1, (short) 1);
    }

    @Bean
    public NewTopic frontSaveProduct() {
        return new NewTopic("saveProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic save() {
        return new NewTopic("save", 1, (short) 1);
    }

    @Bean
    public NewTopic frontDeleteProduct() {
        return new NewTopic("frontDeleteProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic frontUpdateProduct() {
        return new NewTopic("frontUpdateProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic sendProductFromDB() {
        return new NewTopic("sendProductFromDB", 1, (short) 1);
    }
//    @Bean
//    public NewTopic sendProductToFront() {
//        log.info("Create orchestrator topic sendProductToFront");
//        return new NewTopic("sendProductToFront", 1, (short) 1);
//    }

    @Bean
    public NewTopic sendAllProduct() {
        return new NewTopic("sendAllProduct", 1, (short) 1);
    }

    @Bean
    public NewTopic sendParsedString() {
        return new NewTopic("sendParsedString", 1, (short) 1);
    }

    @Bean
    public NewTopic frontSaveOrder() {
        return new NewTopic("frontSaveOrder", 1, (short) 1);
    }

    @Bean
    public NewTopic frontGetOrder() {
        return new NewTopic("frontGetOrder", 1, (short) 1);
    }

    @Bean
    public NewTopic frontDeleteOrder() {
        return new NewTopic("frontDeleteOrder", 1, (short) 1);
    }

    @Bean
    public NewTopic frontUpdateOrder() {
        return new NewTopic("frontUpdateOrder", 1, (short) 1);
    }

    @Bean
    public NewTopic frontSaveUser() {
        return new NewTopic("frontSaveUser", 1, (short) 1);
    }
    @Bean
    public NewTopic frontGetUser() {
        return new NewTopic("frontGetUser", 1, (short) 1);
    }
    @Bean
    public NewTopic frontDeleteUser() {
        return new NewTopic("frontDeleteUser", 1, (short) 1);
    }
    @Bean
    public NewTopic frontUpdateUser() {
        return new NewTopic("frontUpdateUser", 1, (short) 1);
    }

    @Bean
    public NewTopic sendRate() {
        return new NewTopic("saveRateDB", 1, (short) 1);
    }

    @Bean
    public NewTopic sendRateIdProduct() {
        return new NewTopic("sendIdProductRateDB", 1, (short) 1);
    }
}
