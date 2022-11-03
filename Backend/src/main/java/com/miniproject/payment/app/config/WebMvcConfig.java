package com.miniproject.payment.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedHeaders("http://localhost:3000/api/upload")
                .allowedMethods("PUT", "DELETE","GET", "POST").
                allowedHeaders("*");
    }

}
