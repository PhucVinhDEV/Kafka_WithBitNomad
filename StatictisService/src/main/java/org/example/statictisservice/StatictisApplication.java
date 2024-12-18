package org.example.statictisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@SpringBootApplication
public class StatictisApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatictisApplication.class, args);
    }
    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }
}
