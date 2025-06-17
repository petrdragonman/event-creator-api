package com.petrvalouch.event_creator_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @SuppressWarnings("null")
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("loading... cors mapping");
        String[] allowedOrigins = {"http://localhost:5173/", "http://127.0.0.1:5173/"};
        registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedMethods("*").allowedHeaders("*");
    }
}
