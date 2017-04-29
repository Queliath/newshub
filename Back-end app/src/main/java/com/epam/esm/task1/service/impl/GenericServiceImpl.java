package com.epam.esm.task1.service.impl;

import com.epam.esm.task1.dao.GenericDao;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.Dto;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.dto.validator.DtoValidator;
import com.epam.esm.task1.entity.EntityObject;
import com.epam.esm.task1.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Implementation of the GenericService interface.
 *
 * @author Uladzislau Kastsevich
 */
@Transactional
public abstract class GenericServiceImpl<E extends EntityObject<K>, D extends Dto<K>, K extends Serializable> implements GenericService<D, K> {
    private GenericDao<E, K> genericDao;
    private DtoConverter<E, D> dtoConverter;
    @Autowired
    protected DtoValidator dtoValidator;

    @Override
    public D add(D dto) {
        Assert.notNull(dto);
        Assert.isNull(dto.getId());
        dtoValidator.validate(dto);

        E entity = dtoConverter.getEntity(dto);
        E addedEntity = genericDao.add(entity);
        return dtoConverter.getDto(addedEntity);
    }

    @Override
    public D update(D dto) {
        Assert.notNull(dto);
        Assert.notNull(dto.getId());
        dtoValidator.validate(dto);

        E entityWithGivenId = genericDao.getById(dto.getId());
        if (entityWithGivenId == null) {
            throw new EntityNotFoundException();
        }

        E entity = dtoConverter.getEntity(dto);
        E updatedEntity = genericDao.update(entity);
        return dtoConverter.getDto(updatedEntity);
    }

    @Override
    public void delete(K id) {
        Assert.notNull(id);

        E entityWithGivenId = genericDao.getById(id);
        if (entityWithGivenId == null) {
            throw new EntityNotFoundException();
        }

        genericDao.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public D getById(K id) {
        Assert.notNull(id);

        E entity = genericDao.getById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        return dtoConverter.getDto(entity);
    }

    public void setGenericDao(GenericDao<E, K> genericDao) {
        this.genericDao = genericDao;
    }

    public void setDtoConverter(DtoConverter<E, D> dtoConverter) {
        this.dtoConverter = dtoConverter;
    }
}
