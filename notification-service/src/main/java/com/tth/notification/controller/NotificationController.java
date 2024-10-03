package com.tth.notification.controller;

import com.tth.commonlibrary.event.dto.NotificationEvent;
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
        Recipient recipient = Recipient.builder().email(notificationEvent.getRecipient()).build();
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .to(recipient)
                .subject(notificationEvent.getSubject())
                .htmlContent(notificationEvent.getBody())
                .build();

        this.emailService.sendEmail(emailRequest);
    }

}
