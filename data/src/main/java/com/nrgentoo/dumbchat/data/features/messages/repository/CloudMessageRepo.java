package com.nrgentoo.dumbchat.data.features.messages.repository;

import com.nrgentoo.dumbchat.data.features.messages.cloud.ChatApi;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Cloud repository for messages
 */

public class CloudMessageRepo implements MessageRepo {

    @Inject
    ChatApi mChatApi;

    @Inject
    public CloudMessageRepo() {
    }

    @Override
    public Message get(long id) {
        throw new IllegalStateException("Can't get message by id from cloud");
    }

    @Override
    public List<Message> getAll() {
        return mChatApi.getMessageHistory();
    }

    @Override
    public Message getLastMessage() {
        throw new IllegalStateException("Can't get last message from cloud");
    }

    @Override
    public void save(Message entity) throws Throwable {
        throw new IllegalStateException("Can't save message to cloud");
    }

    @Override
    public void save(Collection<Message> entities) throws Throwable {
        throw new IllegalStateException("Can't save collection of messages to cloud");
    }

    @Override
    public void delete(Message entity) throws Throwable {
        throw new IllegalStateException("Can't delete message from cloud");
    }
}
