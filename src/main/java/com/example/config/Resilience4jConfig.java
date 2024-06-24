package com.example.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author libiao
 * @date 2024/3/19
 */
// @Configuration
public class Resilience4jConfig {
    

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(){
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)   // 以百分比配置失败率阈值。当失败率等于或大于阈值时，断路器状态并关闭变为开启，并进行服务降级
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)   // 配置滑动窗口的大小
                .slowCallRateThreshold(50) // 以百分比的方式配置，断路器把调用时间大于slowCallDurationThreshold的调用视为慢调用，当慢调用比例大于等于阈值时，断路器开启，并进行服务降级
                .slowCallDurationThreshold(Duration.ofMillis(500))  // 配置调用时间的阈值，高于该阈值的呼叫视为慢调用，并增加慢调用比例
                .minimumNumberOfCalls(2)
                .waitDurationInOpenState(Duration.ofMillis(5000))  // 断路器从开启过渡到半开应等待的时间
                .permittedNumberOfCallsInHalfOpenState(2)   // 断路器在半开状态下允许通过的调用次数
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        circuitBreakerRegistry.circuitBreaker("backendA");
        return circuitBreakerRegistry;
    }

}
