package com.tth.shipping.controller.internal;

import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.event.dto.NotificationEvent;
import com.tth.shipping.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducerService notificationProducerService;
    private final SampleDataService sampleDataService;

    @KafkaListener(topics = "sample-data", groupId = "shipment-service-group")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        if (notificationEvent.getRecipient().equals("SHIPMENT_SERVICE")) {
            if (this.sampleDataService.createSampleData()) {
                NotificationEvent event = NotificationEvent.builder()
                        .recipient("NOTIFICATION_SERVICE")
                        .body("Sample data created successfully")
                        .build();
                this.notificationProducerService.sendNotification("sampledata-success", event);
            } else {
                NotificationEvent event = NotificationEvent.builder()
                        .recipient("NOTIFICATION_SERVICE")
                        .body("Sample data creation failed")
                        .build();
                this.notificationProducerService.sendNotification("sampledata-failed", event);
            }
        }
    }

}
