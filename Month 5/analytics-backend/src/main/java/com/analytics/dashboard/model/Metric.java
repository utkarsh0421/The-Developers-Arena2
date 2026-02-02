package com.analytics.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "metrics")
public class Metric {
    @Id
    private String id;
    private String name;
    private String description;
    private String type;
    private double value;
    private Instant timestamp;
    private String[] tags;
}
