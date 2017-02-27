package com.nrgentoo.dumbchat.presentation.features.notification;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.notification.NotificationService;

import javax.inject.Inject;

/**
 *Implementation of {@link NotificationService}
 */

public class NotificationServiceImpl implements NotificationService {

    @Inject
    public NotificationServiceImpl() {
    }

    @Override
    public void notifyNewMessage(Message message) {
        // TODO
    }
}
