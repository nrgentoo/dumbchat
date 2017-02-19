package com.nrgentoo.dumbchat.data.core.db;

import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;

/**
 * {@link DbContext} implementation
 */
public class DbContextImpl implements DbContext {

    private final SQLiteDatabase mDb;

    @Inject
    public DbContextImpl(DbOpenHelper dbOpenHelper) {
        mDb = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return mDb;
    }

    @Override
    public void startTransaction() {
        mDb.beginTransaction();
    }

    @Override
    public void markTransactionSuccessful() {
        mDb.setTransactionSuccessful();
    }

    @Override
    public void endTransaction() {
        mDb.endTransaction();
    }
}
