package org.NotificationService.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory("../") // Trỏ về thư mục gốc chứa file .env
                .filename("bitznomad.env")
                .ignoreIfMissing()
                .load();
    }
}