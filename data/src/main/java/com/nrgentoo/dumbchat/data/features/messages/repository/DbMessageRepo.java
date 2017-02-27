package com.nrgentoo.dumbchat.data.features.messages.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.features.messages.model.MessageDbo;
import com.nrgentoo.dumbchat.data.features.messages.model.MessageMapper;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Database implementation of {@link MessageRepo}
 */

public class DbMessageRepo implements MessageRepo {

    @Inject
    DbContext mDbContext;

    @Inject
    UserRepo mUserRepo;

    @Inject
    AttachmentRepo mAttachmentRepo;

    @Inject
    public DbMessageRepo() {
    }

    @Override
    public Message get(long id) {
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                MessageTable.TABLE_NAME +
                " WHERE " + MessageTable.COLUMN_ID + "=?", selectionArgs);

        Message message = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            message = getMessage(cursor);
        }
        cursor.close();

        return message;
    }

    @Override
    public List<Message> getAll() {
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                MessageTable.TABLE_NAME, null);

        List<Message> messages = new LinkedList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            messages.add(getMessage(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return messages;
    }

    @Override
    public Message getLastMessage() {
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                MessageTable.TABLE_NAME +
                " WHERE " + MessageTable.COLUMN_ID + "= (SELECT MAX(" +
                MessageTable.COLUMN_ID + ") FROM " + MessageTable.TABLE_NAME + ")", null);

        Message message = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            message = getMessage(cursor);
        }
        cursor.close();

        return message;
    }

    private Message getMessage(Cursor cursor) {
        MessageDbo messageDbo = MessageTable.parseCursor(cursor);
        User author = mUserRepo.get(messageDbo.authorId());
        List<Attachment<?>> attachments = mAttachmentRepo.getForMessage(messageDbo.id());

        if (author != null) {
            return MessageMapper.transform(messageDbo, author, attachments);
        }

        return null;
    }

    @Override
    public void save(Message message) throws Throwable {
        long localId = mDbContext.getDatabase().insertWithOnConflict(MessageTable.TABLE_NAME,
                null, MessageTable.toContentValues(message), SQLiteDatabase.CONFLICT_REPLACE);
        message.setId(localId);

        if (message.getAttachments() != null) {
            for (Attachment<?> attachment : message.getAttachments()) {
                attachment.setMessageId(localId);
            }
            mAttachmentRepo.save(message.getAttachments());
        }
    }

    @Override
    public void save(Collection<Message> messages) throws Throwable {
        for (Message message : messages) {
            save(message);
        }
    }

    @Override
    public void delete(Message message) throws Throwable {
        deleteAttachments(message.getAttachments());

        String[] selectionArgs = new String[]{String.valueOf(message.getId())};
        mDbContext.getDatabase().delete(MessageTable.TABLE_NAME, MessageTable.COLUMN_ID + "=?",
                selectionArgs);
    }

    private void deleteAttachments(Collection<Attachment<?>> attachments) throws Throwable {
        for (Attachment<?> attachment : attachments) {
            mAttachmentRepo.delete(attachment);
        }
    }
}
