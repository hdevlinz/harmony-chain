package com.tth.commonlibrary.service;

import com.tth.event.dto.NotificationEvent;

public interface NotificationProducerService {

    void sendNotification(String topic, NotificationEvent notificationEvent);

}
