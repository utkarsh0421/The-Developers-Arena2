package com.analytics.dashboard.controller;

import com.analytics.dashboard.model.Metric;
import com.analytics.dashboard.model.PredictionRequest;
import com.analytics.dashboard.model.PredictionResult;
import com.analytics.dashboard.model.SearchResults;
import com.analytics.dashboard.service.AnalyticsService;
import com.analytics.dashboard.service.WebSocketService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final WebSocketService webSocketService;

    public AnalyticsController(AnalyticsService analyticsService, WebSocketService webSocketService) {
        this.analyticsService = analyticsService;
        this.webSocketService = webSocketService;
    }

    @GetMapping(value = "/metrics/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Metric>> streamMetrics() {
        return analyticsService.getRealTimeMetrics()
                .map(metric -> ServerSentEvent.builder(metric).build())
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/search")
    public Mono<SearchResults> searchMetrics(@RequestParam String query,
                                             @RequestParam(defaultValue = "0") int page) {
        return analyticsService.searchMetrics(query, page);
    }

    @PostMapping("/predict")
    public Mono<PredictionResult> predictTrend(@RequestBody PredictionRequest request) {
        return analyticsService.predictTrend(request);
    }
}
