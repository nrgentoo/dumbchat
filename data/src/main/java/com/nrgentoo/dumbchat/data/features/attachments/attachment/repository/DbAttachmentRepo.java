package com.nrgentoo.dumbchat.data.features.attachments.attachment.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.features.attachments.attachment.model.AttachmentDbo;
import com.nrgentoo.dumbchat.data.features.attachments.attachment.model.AttachmentMapper;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository.DbChatPhotoRepo;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Database implementation of {@link AttachmentRepo}
 */

public class DbAttachmentRepo implements AttachmentRepo {

    @Inject
    DbContext mDbContext;

    @Inject
    DbChatPhotoRepo mDbChatPhotoRepo;

    @Inject
    public DbAttachmentRepo() {
    }

    @Override
    public Attachment<?> get(long id) {
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                AttachmentTable.TABLE_NAME +
                " WHERE " + AttachmentTable.COLUMN_ID + "=?", selectionArgs);

        Attachment<?> attachment = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            attachment = getAttachment(cursor);
        }
        cursor.close();

        return attachment;
    }

    @Override
    public List<Attachment<?>> getAll() {
        String query = "SELECT * FROM " + AttachmentTable.TABLE_NAME;
        return getAttachments(query, null);
    }

    @Override
    public List<Attachment<?>> getForMessage(long messageId) {
        String[] selectionArgs = new String[]{String.valueOf(messageId)};
        String query = "SELECT * FROM " +
                AttachmentTable.TABLE_NAME + " WHERE " +
                AttachmentTable.COLUMN_MESSAGE_ID + "=?";

        return getAttachments(query, selectionArgs);
    }

    private List<Attachment<?>> getAttachments(String query, @Nullable String[] queryArgs) {
        Cursor cursor = mDbContext.getDatabase().rawQuery(query, queryArgs);

        List<Attachment<?>> attachments = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            attachments.add(getAttachment(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return attachments;
    }

    private Attachment<?> getAttachment(Cursor cursor) {
        AttachmentDbo attachmentDbo = AttachmentTable.parseCursor(cursor);
        AttachmentEntity attachmentEntity = getAttachmentEntity(attachmentDbo);
        return AttachmentMapper.transform(attachmentDbo, attachmentEntity);
    }

    private AttachmentEntity getAttachmentEntity(AttachmentDbo attachmentDbo) {
        switch (attachmentDbo.type()) {
            case Attachment.TYPE_PHOTO:
                return mDbChatPhotoRepo.get(attachmentDbo.entityId());
        }

        throw new IllegalArgumentException("Unknown attachment type: " + attachmentDbo.type());
    }

    @Override
    public void save(Attachment<?> attachment) throws Throwable {
        saveAttachmentEntity(attachment);

        long localId = mDbContext.getDatabase().insertWithOnConflict(AttachmentTable.TABLE_NAME,
                null, AttachmentTable.toContentValues(attachment), SQLiteDatabase.CONFLICT_REPLACE);

        attachment.setId(localId);
    }

    private void saveAttachmentEntity(Attachment<?> attachment) throws Throwable {
        switch (attachment.type()) {
            case Attachment.TYPE_PHOTO:
                mDbChatPhotoRepo.save((ChatPhoto) attachment.get());
                return;
        }

        throw new IllegalAccessException("Unknown attachment type: " + attachment.type());
    }

    @Override
    public void save(Collection<Attachment<?>> attachments) throws Throwable {
        for (Attachment<?> attachment : attachments) {
            save(attachment);
        }
    }

    @Override
    public void delete(Attachment<? extends AttachmentEntity> attachment)
            throws IllegalAccessException {
        deleteAttachmentEntity(attachment);

        String[] selectionArgs = new String[]{String.valueOf(attachment.getId())};
        mDbContext.getDatabase().delete(AttachmentTable.TABLE_NAME,
                AttachmentTable.COLUMN_ID + "=?", selectionArgs);
    }

    private void deleteAttachmentEntity(Attachment<? extends AttachmentEntity> attachment)
            throws IllegalAccessException {
        switch (attachment.type()) {
            case Attachment.TYPE_PHOTO:
                mDbChatPhotoRepo.delete((ChatPhoto) attachment.get());
                return;
        }

        throw new IllegalAccessException("Unknown attachment type: " + attachment.type());
    }
}
