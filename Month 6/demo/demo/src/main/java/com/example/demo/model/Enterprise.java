package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "enterprises")
public class Enterprise {
    @Id
    private String id;
    private String name;
    private String data;
}
