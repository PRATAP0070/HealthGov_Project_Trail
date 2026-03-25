package com.cognizant.healthgov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.cognizant.healthgov.model")
@EnableJpaRepositories(basePackages = "com.cognizant.healthgov.repository")
public class HealthGovApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthGovApplication.class, args);
    }
}