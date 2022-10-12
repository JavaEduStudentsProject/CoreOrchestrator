package com.example.orchestrator.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class JsonHamsterItem {
    @Id
    private Integer id;
    private String itemJson;

    @Override
    public String toString() {
        return "" + itemJson;
    }
}
