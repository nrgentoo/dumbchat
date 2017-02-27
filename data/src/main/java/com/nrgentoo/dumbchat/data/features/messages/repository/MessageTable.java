package com.nrgentoo.dumbchat.data.features.messages.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.nrgentoo.dumbchat.data.core.util.CursorHelper;
import com.nrgentoo.dumbchat.data.features.messages.model.MessageDbo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

/**
 * Database table for messages
 */

public final class MessageTable {

    private MessageTable() {
    }

    public static final String TABLE_NAME = "messages";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID               + " INTEGER PRIMARY KEY, " +
            COLUMN_TEXT             + " TEXT, " +
            COLUMN_AUTHOR_ID        + " INTEGER NOT NULL, " +
            COLUMN_TIMESTAMP        + " INTEGER NOT NULL" +
            ");";

    public static ContentValues toContentValues(Message message) {
        ContentValues values = new ContentValues();

        if (message.getId() != null) {
            values.put(COLUMN_ID, message.getId());
        }
        if (message.getText() != null && !message.getText().trim().isEmpty()) {
            values.put(COLUMN_TEXT, message.getText());
        }
        values.put(COLUMN_AUTHOR_ID, message.getAuthor().getId());
        values.put(COLUMN_TIMESTAMP, message.getTimeStamp());

        return values;
    }

    public static MessageDbo parseCursor(Cursor cursor) {
        CursorHelper cursorHelper = CursorHelper.createHelper(cursor);

        return MessageDbo.builder()
                .setId(cursorHelper.getLong(COLUMN_ID))
                .setText(cursorHelper.getString(COLUMN_TEXT))
                .setAuthorId(cursorHelper.getLong(COLUMN_AUTHOR_ID))
                .setTimeStamp(cursorHelper.getLong(COLUMN_TIMESTAMP))
                .build();
    }
}
