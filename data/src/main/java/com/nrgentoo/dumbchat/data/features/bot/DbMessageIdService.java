package com.nrgentoo.dumbchat.data.features.bot;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import javax.inject.Inject;

/**
 * {@link MessageIdService} implementation
 */
public class DbMessageIdService implements MessageIdService {

    @Inject
    MessageRepo mMessageRepo;

    @Inject
    public DbMessageIdService() {
    }

    @Override
    public long getLastMessageId() {
        Message message = mMessageRepo.getLastMessage();
        if (message != null) {
            return message.getId();
        } else {
            return 0;
        }
    }
}
