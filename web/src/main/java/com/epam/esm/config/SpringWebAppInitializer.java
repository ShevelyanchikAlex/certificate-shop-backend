package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan("com.epam.esm")
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class SpringWebAppInitializer extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppInitializer.class, args);
    }
}