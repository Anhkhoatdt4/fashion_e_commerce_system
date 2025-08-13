package com.pbl6.fashion_web_be.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApiSwaggerConfig {
    @Bean
    public OpenAPI swaggerConfig(){
        return new OpenAPI().info(
                new Info()
                        .title("Fashion Ecomerence Web Service API")
                        .version("1.0.0")
                        .description("API documentation for the Fashion E-commerce Web Application, including authentication, products, orders, reviews, and more.")
                        .contact(new Contact()
                                .name("PBL6 Team")
                                .email("support@fashionweb.com")
                                .url("https://fashionweb.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
        ).servers(List.of(
                new Server().url("/api").description("Base URL for the fashion Service API")
        ));
    }
}