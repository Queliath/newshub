package com.epam.esm.task1.dao.impl;

import com.epam.esm.task1.dao.GenericDao;
import com.epam.esm.task1.entity.EntityObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Implementation of the GenericDao interface.
 *
 * @author Uladzislau Kastsevich
 */
public abstract class GenericDaoImpl<E extends EntityObject, K extends Serializable> implements GenericDao<E, K> {
    private Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public E add(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(K id) {
        E entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }

    @Override
    public E getById(K id) {
        return entityManager.find(entityClass, id);
    }
}
