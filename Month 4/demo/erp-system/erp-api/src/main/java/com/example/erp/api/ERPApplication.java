package com.example.erp.api;

import com.example.erp.core.ERPProperties;
import com.example.erp.core.config.CacheConfig;
import com.example.erp.core.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.example.erp"})
@EnableScheduling
@EnableConfigurationProperties(ERPProperties.class)
@EnableAsync
public class ERPApplication {

    public static void main(String[] args) {
        SpringApplication.run(ERPApplication.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        return new CacheConfig().cacheManager();
    }

    @Bean
    public org.springframework.data.domain.AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
