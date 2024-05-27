package com.example.marketproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MarketProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketProjectApplication.class, args);
    }

}
