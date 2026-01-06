package com.example.carrepairshopspringbackend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
//public class CorsConfig{
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Configuring CORS for all endpoints");
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:4200", "http://localhost:5173") // Specify the allowed origin(s)
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD") // Allowed methods
                .allowedHeaders("*")// Allowed headers
                .allowCredentials(true); // Allow credentials (cookies, etc.)
    }

}
