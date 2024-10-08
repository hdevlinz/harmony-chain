package com.tth.commonlibrary.service.impl;

import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.event.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationProducerServiceImpl implements NotificationProducerService {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Override
    public void sendNotification(String topic, NotificationEvent notificationEvent) {
        log.info("Sending notification event with topic {}: {}", topic, notificationEvent);
        this.kafkaTemplate.send(topic, notificationEvent);
    }

}
