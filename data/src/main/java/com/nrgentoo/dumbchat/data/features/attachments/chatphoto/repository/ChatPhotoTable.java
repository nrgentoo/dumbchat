package com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.nrgentoo.dumbchat.data.core.util.CursorHelper;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.model.ChatPhotoDbo;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

/**
 * Database table for {@link ChatPhotoDbo}
 */

public final class ChatPhotoTable {

    private ChatPhotoTable() {
    }

    public static final String TABLE_NAME = "chat_photos";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URI = "uri";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID       + " INTEGER PRIMARY KEY, " +
            COLUMN_URI      + " TEXT NOT NULL" +
            ");";

    public static ContentValues toContentValues(ChatPhoto chatPhoto) {
        ContentValues values = new ContentValues();

        if (chatPhoto.getId() != null) {
            values.put(COLUMN_ID, chatPhoto.getId());
        }
        values.put(COLUMN_URI, chatPhoto.getUri());

        return values;
    }

    public static ChatPhotoDbo parseCursor(Cursor cursor) {
        CursorHelper cursorHelper = CursorHelper.createHelper(cursor);

        return ChatPhotoDbo.builder()
                .setId(cursorHelper.getLong(COLUMN_ID))
                .setUri(cursorHelper.getString(COLUMN_URI))
                .build();
    }
}
