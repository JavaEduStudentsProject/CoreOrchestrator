package com.example.orchestrator.controller;

import com.example.orchestrator.kafka.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
//@RestController
//@Service
//@CrossOrigin
//
//public class Controller {
//
//    @Autowired
//    MessageListener ml;
//
//    @GetMapping("/products")
//    public String getAllProductsFromDB (){
//        String products = ml.listenerGetAllProductsResponse();
//        log.info("Products from Database: {}", products);
//        return products;
//    }
//}
