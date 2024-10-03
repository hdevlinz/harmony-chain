package com.tth.order.controller.internal;

import com.tth.commonlibrary.event.dto.NotificationEvent;
import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.order.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducerService notificationProducerService;
    private final SampleDataService sampleDataService;

    @KafkaListener(topics = "sample-data", groupId = "order-service-group")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        if (notificationEvent.getRecipient().equals("ORDER_SERVICE")) {
            this.sampleDataService.createSampleData();

            NotificationEvent event = NotificationEvent.builder()
                    .chanel("SAMPLE_DATA")
                    .recipient("SHIPPING_SERVICE")
                    .build();
            this.notificationProducerService.sendNotification("sample-data", event);
        }
    }

}
