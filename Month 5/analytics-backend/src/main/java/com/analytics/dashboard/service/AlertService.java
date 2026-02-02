package com.analytics.dashboard.service;

import com.analytics.dashboard.model.Alert;
import com.analytics.dashboard.model.Metric;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class AlertService {

    public Mono<Alert> checkMetric(Metric metric) {
        if ("CPU_USAGE".equals(metric.getType()) && metric.getValue() > 90) {
            return Mono.just(new Alert(
                    UUID.randomUUID().toString(),
                    "CRITICAL",
                    "CPU Usage exceeded 90%: " + metric.getValue(),
                    metric.getId(),
                    Instant.now()
            ));
        } else if ("API_LATENCY".equals(metric.getType()) && metric.getValue() > 400) {
            return Mono.just(new Alert(
                    UUID.randomUUID().toString(),
                    "WARNING",
                    "High API Latency detected: " + metric.getValue() + "ms",
                    metric.getId(),
                    Instant.now()
            ));
        }
        return Mono.empty();
    }
}
