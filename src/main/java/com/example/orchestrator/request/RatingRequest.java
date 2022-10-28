package com.example.orchestrator.request;

import javax.validation.constraints.NotBlank;

public class RatingRequest {
        @NotBlank
        private String rate;

        @NotBlank
        private String idProduct;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}

