package com.tth.notification.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.notification.dto.request.SendEmailRequest;
import com.tth.notification.dto.response.SendEmailResponse;
import com.tth.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/email/send")
    public APIResponse<SendEmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return APIResponse.<SendEmailResponse>builder().result(this.emailService.sendEmail(request)).build();
    }

}
