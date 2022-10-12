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
public class JsonHamsterOrder {
    @Id
    private Integer id;
    private String orderItems;

    @Override
    public String toString() {
        return "" + orderItems;
    }
}