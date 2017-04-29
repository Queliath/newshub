package com.epam.esm.task1.dao.impl;

import com.epam.esm.task1.dao.AuthorDao;
import com.epam.esm.task1.entity.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the AuthorDao interface.
 *
 * @author Uladzislau Kastsevich
 */
@Repository
public class AuthorDaoImpl extends GenericDaoImpl<Author, Long> implements AuthorDao {
    private static final String NEWS_ID_PARAM_NAME = "newsId";
    private static final String NAME_PARAM_NAME = "name";
    private static final String AUTHOR_IDS_RANGE_PARAM_NAME = "authorIds";
    private static final String NAME_PATTERN_PARAM_NAME = "namePattern";

    public AuthorDaoImpl() {
        super(Author.class);
    }

    @Override
    public List<Author> getOrderedByNewsCount(int offset, int limit, boolean desc) {
        String queryName = desc ? Author.GET_ALL_ORDERED_BY_NEWS_COUNT_DESC : Author.GET_ALL_ORDERED_BY_NEWS_COUNT_ASC;

        TypedQuery<Author> query = entityManager.createNamedQuery(queryName, Author.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Author> getByNewsId(Long newsId) {
        TypedQuery<Author> query = entityManager.createNamedQuery(Author.GET_BY_NEWS, Author.class);
        return query.setParameter(NEWS_ID_PARAM_NAME, newsId).getResultList();
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = entityManager.createNamedQuery(Author.GET_BY_NAME, Author.class);
        List<Author> authors = query.setParameter(NAME_PARAM_NAME, name).getResultList();
        return authors.isEmpty() ? null : authors.get(0);
    }

    @Override
    public List<Author> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc) {
        String queryName = desc ? Author.GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_DESC : Author.GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_ASC;

        TypedQuery<Author> query = entityManager.createNamedQuery(queryName, Author.class);
        query.setParameter(NAME_PATTERN_PARAM_NAME, "%" + nameFragment + "%").setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long getCountByIdsRange(Collection<Long> idsRange) {
        TypedQuery<Long> query = entityManager.createNamedQuery(Author.GET_COUNT_BY_IDS_RANGE, Long.class);
        return query.setParameter(AUTHOR_IDS_RANGE_PARAM_NAME, idsRange).getSingleResult();
    }
}
