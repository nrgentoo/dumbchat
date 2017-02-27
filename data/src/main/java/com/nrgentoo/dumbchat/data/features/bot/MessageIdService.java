package com.nrgentoo.dumbchat.data.features.bot;

/**
 * Service to get last message id in local db
 */

public interface MessageIdService {

    long getLastMessageId();
}
