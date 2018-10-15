package com.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Basic functionality extension of {@link com.repository.Repository}
 */

public interface BaseRepository<T, ID extends Serializable> extends Repository {

    /**
     * Find the entity by the index, basically by id.
     *
     * @param id must not be null.
     * @return the entity looked up by id.
     * @throws IllegalArgumentException if id is null.
     */
    T find(ID id);

    /**
     * Return all instances of the type.
     *
     * @return all entities.
     */
    List<T> findAll();

}
