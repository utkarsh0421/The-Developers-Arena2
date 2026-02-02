package com.analytics.dashboard.service;

import com.analytics.dashboard.model.Metric;
import com.analytics.dashboard.model.PredictionRequest;
import com.analytics.dashboard.model.PredictionResult;
import com.analytics.dashboard.model.SearchResults;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    private final ReactiveMongoTemplate mongoTemplate;
    private final WebClient.Builder webClientBuilder;
    private final ElasticsearchOperations elasticsearchOperations;
    private final AlertService alertService;

    public Flux<Metric> getRealTimeMetrics() {
        return Flux.merge(
            getDatabaseMetrics(),
            getApiMetrics(),
            getStreamMetrics()
        )
        .doOnNext(this::processAlerts)
        .buffer(Duration.ofSeconds(5))
        .flatMap(this::aggregateMetrics);
    }

    private void processAlerts(Metric metric) {
        alertService.checkMetric(metric)
                .subscribe(alert -> log.warn("ALERT GENERATED: [{}] {}", alert.getSeverity(), alert.getMessage()));
    }

    private Flux<Metric> getDatabaseMetrics() {
        return mongoTemplate.find(Query.query(new Criteria()), Metric.class)
                .sample(Duration.ofSeconds(2))
                .onErrorResume(e -> Flux.empty());
    }

    private Flux<Metric> getApiMetrics() {
        return webClientBuilder.build().get()
                .uri("http://localhost:8080/external/metrics")
                .retrieve()
                .bodyToFlux(Metric.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(e -> Flux.empty());
    }

    private Flux<Metric> getStreamMetrics() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> new Metric(
                        UUID.randomUUID().toString(),
                        "LIVE_STREAM",
                        "Live generated metric",
                        "CPU_USAGE",
                        ThreadLocalRandom.current().nextDouble(50, 100),
                        Instant.now(),
                        new String[]{"live", "stream"}
                ));
    }

    private Flux<Metric> aggregateMetrics(List<Metric> metrics) {
        return Flux.fromIterable(metrics);
    }

    public Mono<SearchResults> searchMetrics(String query, int page) {
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .fields("name", "description", "tags")
                        .query(query)))
                .withPageable(PageRequest.of(page, 20))
                .build();

        return Mono.fromCallable(() -> elasticsearchOperations.search(searchQuery, Metric.class))
                .subscribeOn(Schedulers.boundedElastic())
                .map(searchHits -> new SearchResults(
                    searchHits.getSearchHits().stream()
                            .map(org.springframework.data.elasticsearch.core.SearchHit::getContent)
                            .collect(Collectors.toList()),
                    searchHits.getTotalHits()
                ))
                .onErrorReturn(new SearchResults(List.of(), 0));
    }

    public Mono<PredictionResult> predictTrend(PredictionRequest request) {
        return webClientBuilder.build().post()
                .uri("https://us-central1-your-project.cloudfunctions.net/predict-trend")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PredictionResult.class)
                .onErrorResume(e -> Mono.just(new PredictionResult(List.of(10.0, 12.0, 14.0), 0.85)));
    }
}
