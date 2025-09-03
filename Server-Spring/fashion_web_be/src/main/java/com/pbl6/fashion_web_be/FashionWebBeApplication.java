package com.pbl6.fashion_web_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FashionWebBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionWebBeApplication.class, args);
    }

}
