package com.tth.commonlibrary.service;

import com.tth.commonlibrary.event.dto.NotificationEvent;

public interface NotificationProducerService {

    void sendNotification(String topic, NotificationEvent notificationEvent);

}
