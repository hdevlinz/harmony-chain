package com.tth.identity.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationEvent {

    private String body;

    private String chanel;

    private String subject;

    private String recipient;

    private String templateCode;

    private Map<String, String> params;
}
