package com.nrgentoo.dumbchat.domain.core.repository;

import java.util.Collection;

/**
 * Unit of work interface. Store changes in one transaction
 */

public interface UnitOfWork {

    /**
     * Insert new entity of type {@link T}
     *
     * @param <T> type of entity
     * @param entity entity to insert
     */
    <T> UnitOfWork insert(T entity);

    /**
     * Insert colletion of entities of type {@link T}
     *
     * @param entities collection of entities to insert
     * @param <T> entity to insert
     */
    <T> UnitOfWork insert(Collection<T> entities);

    /**
     * Update entity of type {@link T}
     *
     * @param <T> type of entity
     * @param entity entity to update
     */
    <T> UnitOfWork update(T entity);

    /**
     * Delete entity of type {@link T}
     *
     * @param <T> type of entity
     * @param entity entity to delete
     */
    <T> UnitOfWork delete(T entity);

    /**
     * Commit changes
     */
    void commit() throws Throwable;

    /**
     * Cancel changes
     */
    void cancel();
}
