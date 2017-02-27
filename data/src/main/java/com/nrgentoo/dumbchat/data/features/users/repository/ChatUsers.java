package com.nrgentoo.dumbchat.data.features.users.repository;

import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * Helper class to insert chat users
 */

public final class ChatUsers {

    public static final long BOT_USER_ID = 1L;
    public static final long MYSELF_USER_ID = 2L;

    public static void insetUsers(SQLiteDatabase db) {
        User botUser = User.builder()
                .id(BOT_USER_ID)
                .name("Bot")
                .avatarUri("https://www.gravatar.com/avatar/2?s=128&d=identicon&r=PG")
                .build();

        User myselfUser = User.builder()
                .id(MYSELF_USER_ID)
                .name("Mr. Smith")
                .avatarUri("https://www.gravatar.com/avatar/1?s=128&d=identicon&r=PG")
                .build();

        insert(botUser, db);
        insert(myselfUser, db);
    }

    private static void insert(User user, SQLiteDatabase db) {
        db.insertWithOnConflict(UserTable.TABLE_NAME, null, UserTable.toContentValues(user),
                SQLiteDatabase.CONFLICT_REPLACE);
    }
}
