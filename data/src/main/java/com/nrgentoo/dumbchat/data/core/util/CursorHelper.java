package com.nrgentoo.dumbchat.data.core.util;

import android.database.Cursor;

/**
 * Cursor helper functions
 */

@SuppressWarnings("unused")
public final class CursorHelper {

    private final Cursor mCursor;

    private CursorHelper(Cursor cursor) {
        this.mCursor = cursor;
    }

    public static CursorHelper createHelper(Cursor cursor) {
        return new CursorHelper(cursor);
    }

    public int getInt(String columnName) {
        return mCursor.getInt(mCursor.getColumnIndexOrThrow(columnName));
    }

    public long getLong(String columnName) {
        return mCursor.getLong(mCursor.getColumnIndexOrThrow(columnName));
    }

    public String getString(String columnName) {
        return mCursor.getString(mCursor.getColumnIndexOrThrow(columnName));
    }
}
