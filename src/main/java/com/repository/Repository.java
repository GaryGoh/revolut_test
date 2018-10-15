package com.repository;

import java.io.Serializable;

/**
 * This repository is the central repository maker interface.
 * Domain repository should always extend this interface to declare the needed methods.
 *
 * @see BaseRepository
 * @param <T> the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
public interface Repository<T, ID extends Serializable> {
}
