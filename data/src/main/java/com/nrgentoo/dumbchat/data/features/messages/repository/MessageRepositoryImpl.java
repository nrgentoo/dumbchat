package com.nrgentoo.dumbchat.data.features.messages.repository;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link MessageRepo}
 */

public class MessageRepositoryImpl implements MessageRepo {

    @Inject
    DbMessageRepo mDbMessageRepo;

    @Inject
    CloudMessageRepo mCloudMessageRepo;

    @Inject
    public MessageRepositoryImpl() {
    }

    @Override
    public Message get(long id) {
        return mDbMessageRepo.get(id);
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = mDbMessageRepo.getAll();

        if (messages.isEmpty()) {
            return mCloudMessageRepo.getAll();
        } else {
            return messages;
        }
    }

    @Override
    public void save(Message entity) throws Throwable {
        mDbMessageRepo.save(entity);
    }

    @Override
    public void save(Collection<Message> entities) throws Throwable {
        mDbMessageRepo.save(entities);
    }

    @Override
    public void delete(Message entity) throws Throwable {
        mDbMessageRepo.delete(entity);
    }
}
