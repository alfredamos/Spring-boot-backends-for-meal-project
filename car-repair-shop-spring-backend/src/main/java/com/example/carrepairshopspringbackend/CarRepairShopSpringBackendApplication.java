package com.example.carrepairshopspringbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class CarRepairShopSpringBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRepairShopSpringBackendApplication.class, args);
    }

}
