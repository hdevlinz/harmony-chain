package com.tth.inventory.controller.internal;

import com.tth.commonlibrary.event.dto.NotificationEvent;
import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.inventory.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducerService notificationProducerService;
    private final SampleDataService sampleDataService;

    @KafkaListener(topics = "sample-data", groupId = "inventory-service-group")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        if (notificationEvent.getRecipient().equals("INVENTORY_SERVICE")) {
            this.sampleDataService.createSampleData();

            NotificationEvent event = NotificationEvent.builder()
                    .chanel("SAMPLE_DATA")
                    .recipient("ORDER_SERVICE")
                    .build();
            this.notificationProducerService.sendNotification("sample-data", event);
        }
    }

}
