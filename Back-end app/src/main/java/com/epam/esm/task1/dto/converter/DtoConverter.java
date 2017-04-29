package com.epam.esm.task1.dto.converter;

import com.epam.esm.task1.dto.Dto;
import com.epam.esm.task1.entity.EntityObject;

import java.util.List;

/**
 * Converts the DTO objects into their entity instances.
 *
 * @author Uladzislau Kastsevich
 */
public interface DtoConverter<E extends EntityObject, D extends Dto> {
    /**
     * Returns a DTO object from the EntityObject object.
     *
     * @param entity an instance of the EntityObject class
     * @return an instance of the DTO class
     */
    D getDto(E entity);

    /**
     * Returns a EntityObject object from the DTO object.
     *
     * @param dto an instance of the DTO class
     * @return an instance of the EntityObject class
     */
    E getEntity(D dto);

    /**
     * Returns a list of DTO objects from a list of entity object.
     *
     * @param entityList a list of entity objects
     * @return a list of DTO objects
     */
    List<D> getDtoList(List<E> entityList);
}
