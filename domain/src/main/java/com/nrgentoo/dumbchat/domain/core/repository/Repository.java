package com.nrgentoo.dumbchat.domain.core.repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository interface
 */

public interface Repository<T> {

    /**
     * Get entity from repository by id
     *
     * @param id id to query
     * @return {@link T} entity
     */
    T get(long id);

    /**
     * Get all entities
     *
     * @return {@link Collection} of entities
     */
    List<T> getAll();

    /**
     * Insert or update entity
     *
     * @param entity entity to save
     */
    void save(T entity) throws Throwable;

    /**
     * Insert or update a range of entities
     *
     * @param entities {@link Collection} of entities to save
     */
    void save(Collection<T> entities) throws Throwable;

    /**
     * Delete entity
     *
     * @param entity entity to delete
     */
    void delete(T entity) throws Throwable;
}
