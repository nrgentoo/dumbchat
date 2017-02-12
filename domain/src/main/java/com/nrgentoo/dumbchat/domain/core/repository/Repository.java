package com.nrgentoo.dumbchat.domain.core.repository;

import java.util.Collection;

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
    Collection<T> getAll();

    /**
     * Add new entity
     *
     * @param entity entity to add
     */
    void add(T entity);

    /**
     * Add a range of new entities
     *
     * @param entities {@link Collection} of entities to insert
     */
    void addRange(Collection<T> entities);

}
