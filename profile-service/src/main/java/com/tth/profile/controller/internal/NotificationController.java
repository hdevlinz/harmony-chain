package com.tth.profile.controller.internal;

import com.tth.commonlibrary.event.dto.NotificationEvent;
import com.tth.commonlibrary.service.NotificationProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducerService notificationProducerService;

    @KafkaListener(topics = "sample-data", groupId = "profile-service-group")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        if (notificationEvent.getRecipient().equals("PROFILE_SERVICE")) {
            NotificationEvent event = NotificationEvent.builder()
                    .chanel("SAMPLE_DATA")
                    .recipient("IDENTITY_SERVICE")
                    .build();
            this.notificationProducerService.sendNotification("sample-data", event);
        }
    }

}
