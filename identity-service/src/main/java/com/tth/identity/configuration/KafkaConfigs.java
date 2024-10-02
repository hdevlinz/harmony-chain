package com.tth.identity.configuration;

import com.tth.event.dto.NotificationEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfigs {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    @Bean
    public KafkaTemplate<String, NotificationEvent> kafkaTemplate(ProducerFactory<String, NotificationEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic taskTopic() {
        return TopicBuilder.name("notification-delivery")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
