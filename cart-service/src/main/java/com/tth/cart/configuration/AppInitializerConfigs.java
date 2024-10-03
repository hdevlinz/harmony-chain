package com.tth.cart.configuration;

import com.tth.commonlibrary.event.dto.NotificationEvent;
import com.tth.commonlibrary.service.NotificationProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppInitializerConfigs {

    @Bean
    public ApplicationRunner applicationRunner(NotificationProducerService notificationProducerService) {
        return args -> {
            log.info("Initializing application.....");

            NotificationEvent event = NotificationEvent.builder()
                    .chanel("SAMPLE_DATA")
                    .recipient("PROFILE_SERVICE")
                    .build();
            notificationProducerService.sendNotification("sample-data", event);

            log.info("Application initialization completed.....");
        };
    }

}
