package com.nrgentoo.dumbchat.data.features.attachments.attachment.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.nrgentoo.dumbchat.data.core.util.CursorHelper;
import com.nrgentoo.dumbchat.data.features.attachments.attachment.model.AttachmentDbo;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;

/**
 * Database table for {@link AttachmentDbo}
 */

public final class AttachmentTable {

    private AttachmentTable() {
    }

    public static final String TABLE_NAME = "attachments";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MESSAGE_ID = "message_id";
    public static final String COLUMN_ENTITY_ID = "entity_id";
    public static final String COLUMN_TYPE = "type";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               + " INTEGER PRIMARY KEY, " +
            COLUMN_MESSAGE_ID       + " INTEGER NOT NULL, " +
            COLUMN_ENTITY_ID        + " INTEGER NOT NULL, " +
            COLUMN_TYPE             + " TEXT NOT NULL" +
            ");";

    public static ContentValues toContentValues(Attachment<? extends AttachmentEntity> attachment) {
        if (attachment.getMessageId() == null) {
            throw new IllegalArgumentException("Can't insert attachment with null "
                    + COLUMN_MESSAGE_ID);
        }

        if (attachment.get() == null || attachment.get().getId() == null) {
            throw new IllegalArgumentException("Can't insert attachment with null attachment " +
                    "entity id");
        }

        ContentValues values = new ContentValues();

        if (attachment.getId() != null) {
            values.put(COLUMN_ID, attachment.getId());
        }
        values.put(COLUMN_MESSAGE_ID, attachment.getMessageId());
        values.put(COLUMN_ENTITY_ID, attachment.get().getId());
        values.put(COLUMN_TYPE, attachment.type());

        return values;
    }

    public static AttachmentDbo parseCursor(Cursor cursor) {
        CursorHelper cursorHelper = CursorHelper.createHelper(cursor);

        return AttachmentDbo.builder()
                .setId(cursorHelper.getLong(COLUMN_ID))
                .setMessageId(cursorHelper.getLong(COLUMN_MESSAGE_ID))
                .setEntityId(cursorHelper.getLong(COLUMN_ENTITY_ID))
                .setType(cursorHelper.getString(COLUMN_TYPE))
                .build();
    }
}
