package com.nrgentoo.dumbchat.data.features.bot;

import com.nrgentoo.dumbchat.data.features.messages.model.MessageDto;

import io.reactivex.Flowable;

/**
 * Bot service
 */

public interface BotService {

    Flowable<MessageDto> messages();
}
