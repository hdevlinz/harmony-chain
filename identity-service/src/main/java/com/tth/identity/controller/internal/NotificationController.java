package com.tth.identity.controller.internal;

import com.tth.commonlibrary.service.NotificationProducerService;
import com.tth.event.dto.NotificationEvent;
import com.tth.identity.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/sampledata", produces = "application/json; charset=UTF-8")
public class NotificationController {

    private final NotificationProducerService notificationProducerService;
    private final SampleDataService sampleDataService;

    @GetMapping
    public void createSampleData() {
        if (this.sampleDataService.createSampleData()) {
            NotificationEvent event = NotificationEvent.builder()
                    .chanel("SAMPLE_DATA")
                    .recipient("PRODUCT_SERVICE")
                    .build();
            this.notificationProducerService.sendNotification("sample-data", event);
        }
    }

}
