package com.example.carrepairswithticketmanytechmanyspringboo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
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
