package com.nrgentoo.dumbchat.domain.features.bot;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import io.reactivex.Flowable;

/**
 * Bot service
 */

public interface BotService {

    Flowable<Message> messages();
}
