package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.epam.esm")
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class SpringWebAppInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppInitializer.class, args);
    }
}