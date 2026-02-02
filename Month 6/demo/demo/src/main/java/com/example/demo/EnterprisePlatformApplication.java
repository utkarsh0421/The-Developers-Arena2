package com.example.demo;

import com.example.demo.config.EnterpriseProperties;
import com.example.demo.filter.RequestLoggingFilter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(EnterpriseProperties.class)
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableRetry
public class EnterprisePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterprisePlatformApplication.class, args);
    }

    @Bean
    public OpenAPI enterpriseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enterprise Platform API")
                        .version("1.0.0")
                        .description("Production-ready enterprise platform")
                        .contact(new Contact()
                                .name("Enterprise Platform Team")
                                .email("enterprise@platform.com")
                                .url("https://enterprise-platform.com"))
                        .license(new License()
                                .name("Enterprise License")
                                .url("https://enterprise-platform.com/license")))
                .externalDocs(new ExternalDocumentation()
                        .description("Enterprise Platform Documentation")
                        .url("https://docs.enterprise-platform.com"))
                .servers(List.of(
                        new Server().url("https://api.enterprise-platform.com").description("Production server"),
                        new Server().url("https://staging.api.enterprise-platform.com").description("Staging server"),
                        new Server().url("http://localhost:8080").description("Local development")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLoggingFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags(
                "application", "enterprise-platform",
                "environment", System.getenv().getOrDefault("ENVIRONMENT", "development")
        );
    }
}
