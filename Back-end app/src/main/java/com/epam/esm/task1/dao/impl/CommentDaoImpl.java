package com.epam.esm.task1.dao.impl;

import com.epam.esm.task1.dao.CommentDao;
import com.epam.esm.task1.entity.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of CommentDao interface.
 *
 * @author Uladzislau Kastsevich
 */
@Repository
public class CommentDaoImpl extends GenericDaoImpl<Comment, Long> implements CommentDao {
    private static final String NEWS_ID_PARAM_NAME = "newsId";

    public CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getByNewsIdOrderedByCreationTime(Long newsId, boolean desc, int offset, int limit) {
        String queryName = desc ? Comment.GET_BY_NEWS_ORDERED_BY_CREATION_TIME_DESC : Comment.GET_BY_NEWS_ORDERED_BY_CREATION_TIME_ASC;

        TypedQuery<Comment> query = entityManager.createNamedQuery(queryName, Comment.class);
        query.setParameter(NEWS_ID_PARAM_NAME, newsId).setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long getCountByNewsId(Long newsId) {
        TypedQuery<Long> query = entityManager.createNamedQuery(Comment.GET_COUNT_BY_NEWS_ID, Long.class);
        return query.setParameter(NEWS_ID_PARAM_NAME, newsId).getSingleResult();
    }
}
