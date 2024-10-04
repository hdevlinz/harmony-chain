package com.tth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

@Slf4j
@EnableFeignClients
@EnableNeo4jAuditing
@SpringBootApplication
public class ProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            log.info("Initializing application.....");
            // TODO: Implement initialization logic here
            log.info("Application initialization completed.....");
        };
    }

}
