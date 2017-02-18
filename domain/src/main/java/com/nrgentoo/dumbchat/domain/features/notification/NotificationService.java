package com.nrgentoo.dumbchat.domain.features.notification;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

/**
 * Service to send notifications
 */

public interface NotificationService {

    /**
     * Show notification when new message is received
     */
    void notifyNewMessage(Message message);
}
