package com.tth.notification.repository;

import com.tth.notification.dto.request.EmailRequest;
import com.tth.notification.dto.response.SendEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-client", url = "${app.services.email.url}")
public interface EmailClient {

    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    SendEmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest request);

}
