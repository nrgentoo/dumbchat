package com.nrgentoo.dumbchat.domain.features.messages.repository;

import com.nrgentoo.dumbchat.domain.core.repository.Repository;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import java.util.Collection;

/**
 * {@link Repository} for {@link Message}
 */

public interface MessageRepo extends Repository<Message> {

    /**
     * Get new messages
     *
     * @return {@link Collection} of new messages
     */
    Collection<Message> getNewMessages();
}
