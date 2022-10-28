package com.example.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class JsonHamsterComment {

    @Id
    private Integer idComment;

    private Integer productId;

    private Integer rate;

//    private String commentText;

    public JsonHamsterComment(Integer productId, Integer rate) {
        this.productId = productId;
        this.rate = rate;
    }

//    public JsonHamsterComment(Integer productId, Integer rate, String commentText) {
//        this.productId = productId;
//        this.rate = rate;
//        this.commentText = commentText;
//    }

    @Override
    public String toString() {
        return "productId: " + productId + " rate: " + rate;
    }
}
