package com.nrgentoo.dumbchat.domain.core.repository;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;

/**
 * Implementation of {@link UnitOfWork}
 */

@SuppressWarnings("WeakerAccess")
public class UnitOfWorkImpl implements UnitOfWork {

    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";

    @Inject
    MessageRepo mMessageRepo;

    @Inject
    UserRepo mUserRepo;

    @Inject
    AttachmentRepo mAttachmentRepo;

    @Inject
    ChangesStorage mChangesStorage;

    @Inject
    DbContext mDbContext;

    @Inject
    public UnitOfWorkImpl() {
    }

    @Override
    public <T> UnitOfWork insert(T entity) {
        mChangesStorage.attach(entity, INSERT);
        return this;
    }

    @Override
    public <T> UnitOfWork insert(Collection<T> entities) {
        mChangesStorage.attach(entities, INSERT);
        return this;
    }

    @Override
    public <T> UnitOfWork update(T entity) {
        mChangesStorage.attach(entity, UPDATE);
        return this;
    }

    @Override
    public <T> UnitOfWork delete(T entity) {
        mChangesStorage.attach(entity, DELETE);
        return this;
    }

    private <T> void registerEntity(T entity, String state) {
        mChangesStorage.attach(entity, state);
    }

    @Override
    public void commit() throws Throwable {
        try {
            // start transaction
            mDbContext.startTransaction();

            for (EntityState entityState : mChangesStorage) {
                switch (entityState.state()) {
                    case INSERT:
                    case UPDATE:
                        // save
                        Repository repository = getRepo(entityState.entity());
                        if (entityState.entity() instanceof Collection<?>) {
                            //noinspection unchecked
                            repository.save((Collection) entityState.entity());
                        } else {
                            //noinspection unchecked
                            repository.save(entityState.entity());
                        }
                        break;
                    case DELETE:
                        // delete
                        getRepo(entityState.entity()).delete(entityState.entity());
                        break;
                }
            }

            // mark successful
            mDbContext.markTransactionSuccessful();
        } finally {
            // end transaction
            mDbContext.endTransaction();
        }
    }

    @Override
    public void cancel() {
        mChangesStorage.clear();
    }

    @SuppressWarnings("unchecked")
    private <T> Repository<T> getRepo(T entity) {
        if (isTypeOf(entity, Message.class)) {
            return (Repository<T>) mMessageRepo;
        } else if (isTypeOf(entity, User.class)) {
            return (Repository<T>) mUserRepo;
        } else if (isTypeOf(entity, Attachment.class)) {
            return (Repository<T>) mAttachmentRepo;
        }

        throw new IllegalArgumentException("Unknown type. Can't match repository for this type: " +
                entity);
    }

    private <T> boolean isTypeOf(T entity, Class<?> type) {
        if (entity instanceof Collection<?>) {
            Iterator it = ((Collection) entity).iterator();
            return it.hasNext() && type.isInstance(it.next());
        } else {
            return type.isInstance(entity);
        }
    }
}
