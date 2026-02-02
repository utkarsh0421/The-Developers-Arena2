package com.example.demo.service.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    public void logAccess(String event, String id, String message) {
        log.info("AUDIT: [{}] ID: {} - {}", event, id, message);
    }
}
