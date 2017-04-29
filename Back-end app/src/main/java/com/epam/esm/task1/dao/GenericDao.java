package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.EntityObject;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;

/**
 * Generic Dao interface which declares basic DAO-operations.
 *
 * @author Uladzislau Kastsevich
 */
public interface GenericDao<E extends EntityObject, K extends Serializable> {
    /**
     * Adds an instance of some entity class to the data storage.
     *
     * @param entity an instance of some entity
     * @return an added instance of some entity
     * @throws DataAccessException
     */
    E add(E entity);

    /**
     * Updates an instance of some entity in the data storage.
     *
     * @param entity an instance of some entity
     * @return an updated instance of some entity
     * @throws DataAccessException
     */
    E update(E entity);

    /**
     * Deletes an instance of some entity with the given ID from the data storage.
     *
     * @param id an id of the deleting instance
     * @throws DataAccessException
     */
    void delete(K id);

    /**
     * Gets a certain instance of some entity by the given ID.
     *
     * @param id an id of the needed instance
     * @return a certain instance of some entity
     * @throws DataAccessException
     */
    E getById(K id);
}
