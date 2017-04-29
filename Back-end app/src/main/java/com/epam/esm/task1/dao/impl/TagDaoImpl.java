package com.epam.esm.task1.dao.impl;

import com.epam.esm.task1.dao.TagDao;
import com.epam.esm.task1.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the TagDao interface.
 *
 * @author Uladzislau Kastsevich
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {
    private static final String NEWS_ID_PARAM_NAME = "newsId";
    private static final String NAME_PARAM_NAME = "name";
    private static final String TAG_IDS_RANGE_PARAM_NAME = "tagIds";
    private static final String NAME_PATTERN_PARAM_NAME = "namePattern";

    public TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> getOrderedByNewsCount(int offset, int limit, boolean desc) {
        String queryName = desc ? Tag.GET_ALL_ORDERED_BY_NEWS_COUNT_DESC : Tag.GET_ALL_ORDERED_BY_NEWS_COUNT_ASC;

        TypedQuery<Tag> query = entityManager.createNamedQuery(queryName, Tag.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Tag> getByNewsId(Long newsId) {
        TypedQuery<Tag> query = entityManager.createNamedQuery(Tag.GET_BY_NEWS, Tag.class);
        return query.setParameter(NEWS_ID_PARAM_NAME, newsId).getResultList();
    }

    @Override
    public Tag getByName(String name) {
        TypedQuery<Tag> query = entityManager.createNamedQuery(Tag.GET_BY_NAME, Tag.class);
        List<Tag> tags = query.setParameter(NAME_PARAM_NAME, name).getResultList();
        return tags.isEmpty() ? null : tags.get(0);
    }

    @Override
    public List<Tag> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc) {
        String queryName = desc ? Tag.GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_DESC : Tag.GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_ASC;

        TypedQuery<Tag> query = entityManager.createNamedQuery(queryName, Tag.class);
        query.setParameter(NAME_PATTERN_PARAM_NAME, "%" + nameFragment + "%").setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long getCountByIdsRange(Collection<Long> idsRange) {
        TypedQuery<Long> query = entityManager.createNamedQuery(Tag.GET_COUNT_BY_IDS_RANGE, Long.class);
        return query.setParameter(TAG_IDS_RANGE_PARAM_NAME, idsRange).getSingleResult();
    }
}
