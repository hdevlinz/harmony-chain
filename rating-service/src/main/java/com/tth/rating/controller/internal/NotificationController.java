package com.tth.rating.controller.internal;

import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.event.dto.NotificationEvent;
import com.tth.rating.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducerService notificationProducerService;
    private final SampleDataService sampleDataService;

    @KafkaListener(topics = "sample-data", groupId = "rating-service-group")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        if (notificationEvent.getRecipient().equals("RATING_SERVICE")) {
            if (this.sampleDataService.createSampleData()) {
                NotificationEvent event = NotificationEvent.builder()
                        .chanel("SAMPLE_DATA")
                        .recipient("INVENTORY_SERVICE")
                        .build();
                this.notificationProducerService.sendNotification("sample-data", event);
            }
        }
    }

}
