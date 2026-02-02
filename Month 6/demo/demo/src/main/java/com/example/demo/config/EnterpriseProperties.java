package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "enterprise")
public class EnterpriseProperties {
    private String version = "1.0.0";
    private String environment = "development";
    private boolean featureEnabled = true;
}
