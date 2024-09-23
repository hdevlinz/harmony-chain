package com.tth.notification.controller;

import com.tth.event.dto.NotificationEvent;
import com.tth.notification.dto.request.Recipient;
import com.tth.notification.dto.request.SendEmailRequest;
import com.tth.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        this.emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder().email(notificationEvent.getRecipient()).build())
                .subject(notificationEvent.getSubject())
                .htmlContent(notificationEvent.getBody())
                .build());
    }

}
