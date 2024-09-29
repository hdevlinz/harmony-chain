package com.tth.notification.service;

import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.notification.dto.request.EmailRequest;
import com.tth.notification.dto.request.SendEmailRequest;
import com.tth.notification.dto.request.Sender;
import com.tth.notification.dto.response.SendEmailResponse;
import com.tth.notification.repository.EmailClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    @Value("${app.mail-api-key}")
    private String apiKey;

    public SendEmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(List.of(request.getTo()))
                .htmlContent(request.getHtmlContent())
                .sender(Sender.builder()
                        .email("hieptt.2003@gmail.com")
                        .name("Harmony SCMS")
                        .build())
                .subject(request.getSubject())
                .build();
        try {
            return this.emailClient.sendEmail(this.apiKey, emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

}
