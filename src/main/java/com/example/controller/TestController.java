package com.example.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author libiao
 * @date 2024/3/19
 */
@Slf4j
@RestController
public class TestController {

    public static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";

    // @Retry(name = "retryA", fallbackMethod = "fallback")
    // @Bulkhead(name = "bulkHeadA", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "fallback")
    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    @GetMapping("/demo")
    public String demo() {
        int anInt = new Random().nextInt(100);
        // if(anInt >= 70){
        //     throw new RuntimeException("aaa");
        // }
        // if(true){
        //     throw new RuntimeException("aaa");
        // }
        try{
            Thread.sleep(1000L);
        }catch (Exception e){

        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FMT);
        System.out.println(dtf.format(LocalDateTime.now()) + "  demo run ...");
        return "demo";
    }

    public String fallback(CallNotPermittedException exception) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FMT);
        System.out.println(dtf.format(LocalDateTime.now()) + "  fallback  " + exception.getCausingCircuitBreakerName());
        return "fallback";
    }

}
