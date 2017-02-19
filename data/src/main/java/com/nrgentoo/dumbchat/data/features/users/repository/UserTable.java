package com.nrgentoo.dumbchat.data.features.users.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.nrgentoo.dumbchat.data.core.util.CursorHelper;
import com.nrgentoo.dumbchat.data.features.users.model.UserDbo;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * Database table for {@link UserDbo}
 */

public final class UserTable {

    private UserTable() {
    }

    public static final String TABLE_NAME = "users";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AVATAR_URI = "avatar_uri";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID           + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME         + " TEXT NOT NULL, " +
            COLUMN_AVATAR_URI   + " TEXT NOT NULL" +
            ");";

    public static ContentValues toContentValues(User user) {
        ContentValues values = new ContentValues();

        if (user.getId() != null) {
            values.put(COLUMN_ID, user.getId());
        }
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_AVATAR_URI, user.getAvatarUri());

        return values;
    }

    public static UserDbo parseCursor(Cursor cursor) {
        CursorHelper cursorHelper = CursorHelper.createHelper(cursor);

        return UserDbo.builder()
                .setId(cursorHelper.getLong(COLUMN_ID))
                .setName(cursorHelper.getString(COLUMN_NAME))
                .setAvatarUri(cursorHelper.getString(COLUMN_AVATAR_URI))
                .build();
    }
}
