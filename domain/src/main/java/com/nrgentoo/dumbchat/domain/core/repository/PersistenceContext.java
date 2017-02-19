package com.nrgentoo.dumbchat.domain.core.repository;

/**
 * Interface for persistence context
 */

public interface PersistenceContext {

    void startTransaction();

    void markTransactionSuccessful();

    void endTransaction();
}
