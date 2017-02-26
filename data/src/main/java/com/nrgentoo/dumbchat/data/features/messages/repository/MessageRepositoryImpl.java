package com.nrgentoo.dumbchat.data.features.messages.repository;

import android.util.Log;

import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Implementation of {@link MessageRepo}
 */

public class MessageRepositoryImpl implements MessageRepo {

    private static final String TAG = "MessageRepositoryImpl";

    @Inject
    DbMessageRepo mDbMessageRepo;

    @Inject
    Lazy<UnitOfWork> mUnitOfWork;

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
            messages = mCloudMessageRepo.getAll();
            saveMessages(messages);
        }

        return messages;
    }

    private void saveMessages(List<Message> messages) {
        try {
            UnitOfWork unitOfWork = mUnitOfWork.get();

            unitOfWork.insert(messages);
            unitOfWork.commit();
        } catch (Throwable throwable) {
            Log.e(TAG, "Error while saving messages to local db");
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
