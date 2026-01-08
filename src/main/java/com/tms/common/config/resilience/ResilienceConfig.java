package com.tms.common.config.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Centralized resilience configuration for all services.
 * Provides circuit breaker, retry, and timeout patterns.
 */
@Configuration
public class ResilienceConfig {

    // ==========================================
    // CIRCUIT BREAKER
    // ==========================================

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig defaultConfig = CircuitBreakerConfig.custom()
            // When 50% of calls fail, open the circuit
            .failureRateThreshold(50)
            // Number of calls to calculate failure rate
            .slidingWindowSize(10)
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            // Minimum calls before calculating failure rate
            .minimumNumberOfCalls(5)
            // Wait 30 seconds before transitioning to half-open
            .waitDurationInOpenState(Duration.ofSeconds(30))
            // Number of calls in half-open state
            .permittedNumberOfCallsInHalfOpenState(3)
            // Automatically transition from open to half-open
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            // Record these exceptions as failures
            .recordExceptions(Exception.class)
            // Don't record these as failures
            .ignoreExceptions(IllegalArgumentException.class)
            .build();

        return CircuitBreakerRegistry.of(defaultConfig);
    }

    // ==========================================
    // RETRY
    // ==========================================

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig defaultConfig = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryExceptions(Exception.class)
            .ignoreExceptions(IllegalArgumentException.class)
            // Exponential backoff
            .intervalFunction(attempt -> Duration.ofMillis(500 * (long) Math.pow(2, attempt - 1)).toMillis())
            .build();

        return RetryRegistry.of(defaultConfig);
    }

    // ==========================================
    // TIME LIMITER
    // ==========================================

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig defaultConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(3))
            .cancelRunningFuture(true)
            .build();

        return TimeLimiterRegistry.of(defaultConfig);
    }

    // ==========================================
    // NAMED CONFIGURATIONS
    // ==========================================

    public static final String DATABASE_CIRCUIT_BREAKER = "database";
    public static final String EXTERNAL_API_CIRCUIT_BREAKER = "externalApi";
    public static final String KAFKA_CIRCUIT_BREAKER = "kafka";
    public static final String REDIS_CIRCUIT_BREAKER = "redis";
}
