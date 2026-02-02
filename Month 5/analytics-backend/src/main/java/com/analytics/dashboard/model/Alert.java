package com.analytics.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {
    private String id;
    private String severity; // INFO, WARNING, CRITICAL
    private String message;
    private String metricId;
    private Instant timestamp;
}
