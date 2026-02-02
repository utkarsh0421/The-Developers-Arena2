package com.example.demo.service;

import com.example.demo.model.Enterprise;
import com.example.demo.model.EnterpriseDTO;
import com.example.demo.model.Report;
import com.example.demo.repository.EnterpriseRepository;
import com.example.demo.service.support.*;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final CacheManager cacheManager;
    private final MeterRegistry metricRegistry;
    private final AuditService auditService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final ReportService reportService;
    private final CloudStorageService cloudStorageService;
    private final NotificationService notificationService;
    private final AlertService alertService;

    @Retryable(value = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Cacheable(value = "enterpriseData", key = "#id", unless = "#result == null")
    public EnterpriseDTO getEnterpriseData(String id) {
        Timer.Sample sample = Timer.start(metricRegistry);
        try {
            log.info("Fetching enterprise data for id: {}", id);
            
            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("enterpriseService");
            
            return circuitBreaker.executeSupplier(() -> {
                Enterprise enterprise = enterpriseRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Enterprise not found"));

                // Audit log
                auditService.logAccess("ENTERPRISE_DATA_ACCESS", id, "Data accessed");

                return convertToDTO(enterprise);
            });
        } finally {
            sample.stop(metricRegistry.timer("enterprise.data.get"));
        }
    }

    @Async
    @Scheduled(cron = "0 0 3 * * ?") // Daily at 3 AM
    public void generateDailyReports() {
        log.info("Starting daily report generation");

        try {
            List<Report> reports = reportService.generateAllReports();

            // Upload to cloud storage
            reports.forEach(report -> {
                cloudStorageService.uploadReport(report);
                notificationService.sendReportNotification(report);
            });

            metricRegistry.counter("reports.generated").increment(reports.size());
            log.info("Generated {} daily reports", reports.size());
        } catch (Exception e) {
            log.error("Error generating daily reports", e);
            metricRegistry.counter("reports.errors").increment();
            alertService.sendAlert("Report generation failed", e);
        }
    }

    private EnterpriseDTO convertToDTO(Enterprise enterprise) {
        return new EnterpriseDTO(enterprise.getId(), enterprise.getName(), enterprise.getData());
    }
}
