package com.epam.esm.task1.service;

import org.springframework.dao.DataAccessException;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.dto.Dto;

import java.io.Serializable;

/**
 * Generic Service interface which declares basic business-logic operations.
 *
 * @author Uladzislau Kastsevich
 */
public interface GenericService<D extends Dto, K extends Serializable> {
    /**
     * Adds an instance of some dto class to the data storage.
     *
     * @param dto an instance of some dto class
     * @return an added instance of some dto class
     * @throws DataAccessException
     * @throws DtoValidationException
     */
    D add(D dto);

    /**
     * Updates an instance of some dto class to the data storage.
     *
     * @param dto an instance of some dto class
     * @return an updated instance of some dto class
     * @throws DataAccessException
     * @throws EntityNotFoundException
     * @throws DtoValidationException
     */
    D update(D dto);

    /**
     * Deletes an instance of some dto with the given ID from the data storage.
     *
     * @param id an id of the deleting instance
     * @throws DataAccessException
     * @throws EntityNotFoundException
     */
    void delete(K id);

    /**
     * Gets a certain instance of some dto by the given ID.
     *
     * @param id an id of the needed instance
     * @return a certain instance of some dto
     * @throws DataAccessException
     * @throws EntityNotFoundException
     */
    D getById(K id);
}
