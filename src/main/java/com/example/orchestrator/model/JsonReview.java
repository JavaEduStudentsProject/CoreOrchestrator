package com.example.orchestrator.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class JsonReview {
    @Id
    @Generated
    private String id;
    private String productId;
    private String rating;
    private String userId;
    private String review;

    public JsonReview() {
    }

    public JsonReview(String productId, String rating, String userId, String review) {
        this.productId = productId;
        this.rating = rating;
        this.userId = userId;
        this.review = review;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"productId\":\"" + productId + '\"' +
                ", \"rating\":\"" + rating + '\"' +
                ", \"userId\":\"" + userId + '\"' +
                ", \"review\":\"" + review + '\"' +
                "}";
    }
}
