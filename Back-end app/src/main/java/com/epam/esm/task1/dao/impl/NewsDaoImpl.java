package com.epam.esm.task1.dao.impl;

import com.epam.esm.task1.dao.NewsDao;
import com.epam.esm.task1.entity.Author;
import com.epam.esm.task1.entity.News;
import com.epam.esm.task1.entity.Tag;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Implementation of the NewsDao interface.
 *
 * @author Uladzisau Kastsevich
 */
@Repository
public class NewsDaoImpl extends GenericDaoImpl<News, Long> implements NewsDao {
    private static final String NEWS_IDS_RANGE_PARAM_NAME = "newsIds";
    private static final String ID_PARAM_NAME = "id";

    public NewsDaoImpl() {
        super(News.class);
    }

    @Override
    public News getByIdWithRelations(Long id) {
        TypedQuery<News> query = entityManager.createNamedQuery(News.GET_BY_ID, News.class);

        List<News> newsList = query.setParameter(ID_PARAM_NAME, id).getResultList();
        if (newsList.isEmpty()) {
            return null;
        }

        News news = newsList.get(0);
        setCommentsCountForSingleNews(news);
        return news;
    }

    @Override
    public List<News> getOrderedByCreationTime(boolean desc, int offset, int limit) {
        String queryForIdsRangeName = desc ? News.GET_IDS_RANGE_ORDERED_BY_CREATION_TIME_DESC : News.GET_IDS_RANGE_ORDERED_BY_CREATION_TIME_ASC;
        String queryForNewsName = desc ? News.GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_DESC : News.GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_ASC;

        TypedQuery<Long> queryForIdsRange = entityManager.createNamedQuery(queryForIdsRangeName, Long.class);
        List<Long> newsIds = queryForIdsRange.setFirstResult(offset).setMaxResults(limit).getResultList();
        if (newsIds.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<News> queryForNews = entityManager.createNamedQuery(queryForNewsName, News.class);
        List<News> newsList = queryForNews.setParameter(NEWS_IDS_RANGE_PARAM_NAME, newsIds).getResultList();

        setCommentsCountForNewsList(newsList);
        return newsList;
    }

    @Override
    public long getCount() {
        return entityManager.createNamedQuery(News.GET_COUNT, Long.class).getSingleResult();
    }

    @Override
    public List<News> getByTagsAndAuthorsOrderedByCreationTime(Collection<Long> tagIds, Collection<Long> authorIds, boolean desc, int offset, int limit) {
        List<Long> newsIds = getIdsRangeOrderedByCreationTime(tagIds, authorIds, desc, offset, limit);
        if (newsIds.isEmpty()) {
            return Collections.emptyList();
        }

        String queryName = desc ? News.GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_DESC : News.GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_ASC;

        TypedQuery<News> query = entityManager.createNamedQuery(queryName, News.class);
        List<News> newsList = query.setParameter(NEWS_IDS_RANGE_PARAM_NAME, newsIds).getResultList();

        setCommentsCountForNewsList(newsList);
        return newsList;
    }

    @Override
    public long getCountByTagsAndAuthors(Collection<Long> tagIds, Collection<Long> authorIds) {
        List<Long> newsIds = getIdsRange(tagIds, authorIds);
        return newsIds.size();
    }

    private News setCommentsCountForSingleNews(News news) {
        TypedQuery<Long> query = entityManager.createNamedQuery(News.GET_COMMENTS_COUNT, Long.class);
        Long commentCount = query.setParameter(ID_PARAM_NAME, news.getId()).getSingleResult();

        news.setCommentsCount(commentCount);
        return news;
    }

    private List<News> setCommentsCountForNewsList(List<News> newsList) {
        TypedQuery<News> query = entityManager.createNamedQuery(News.GET_COMMENTS_COUNT_BY_IDS_RANGE, News.class);

        List<Long> newsIds = new ArrayList<>(newsList.size());
        for (News news : newsList) {
            newsIds.add(news.getId());
        }
        query.setParameter(NEWS_IDS_RANGE_PARAM_NAME, newsIds);

        List<News> newsWithCommentsCountList = query.getResultList();

        for (News news : newsList) {
            for (News newsWithCommentCount : newsWithCommentsCountList) {
                if (news.getId().equals(newsWithCommentCount.getId())) {
                    news.setCommentsCount(newsWithCommentCount.getCommentsCount());
                    break;
                }
            }
        }

        return newsList;
    }

    private List<Long> getIdsRangeOrderedByCreationTime(Collection<Long> tagIds, Collection<Long> authorIds,
                                                           boolean desc, int offset, int limit) {
        TypedQuery<Long> query = getTypedQueryForIdsRange(tagIds, authorIds, desc);

        query.setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    private List<Long> getIdsRange(Collection<Long> tagIds, Collection<Long> authorIds) {
        TypedQuery<Long> query = getTypedQueryForIdsRange(tagIds, authorIds, null);

        return query.getResultList();
    }

    private TypedQuery<Long> getTypedQueryForIdsRange(Collection<Long> tagIds, Collection<Long> authorIds, Boolean desc) {
        boolean byTags = tagIds != null && !tagIds.isEmpty();
        boolean byAuthors = authorIds != null && !authorIds.isEmpty();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select(newsRoot.get("id"));

        List<Predicate> predicates = new ArrayList<>();
        if (byTags) {
            Join<News, Tag> tagJoin = newsRoot.join("tags");
            predicates.add(tagJoin.get("id").in(tagIds));
        }
        if (byAuthors) {
            Join<News, Author> authorJoin = newsRoot.join("authors");
            predicates.add(authorJoin.get("id").in(authorIds));
        }

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        criteriaQuery.where(predicates.toArray(predicatesArray));

        criteriaQuery.groupBy(newsRoot.get("id"));

        long tagIdsMultiplier = byTags ? tagIds.size() : 1;
        long authorIdsMultiplier = byAuthors ? authorIds.size() : 1;
        long resultMultiplier = tagIdsMultiplier * authorIdsMultiplier;
        criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(newsRoot.get("id")), resultMultiplier));

        if (desc != null && desc) {
            criteriaQuery.orderBy(criteriaBuilder.desc(newsRoot.get("creationTime")));
        }
        if (desc != null && !desc) {
            criteriaQuery.orderBy(criteriaBuilder.asc(newsRoot.get("creationTime")));
        }

        return entityManager.createQuery(criteriaQuery);
    }
}
