package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.News;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * Provides the DAO-logic related to the News entity.
 *
 * @author Uladzislau Kastsevich
 */
public interface NewsDao extends GenericDao<News, Long> {
    /**
     * Gets a certain news with its authors, tags and comments count.
     *
     * @param id an ID of needed news
     * @return an instance of the News entity
     */
    News getByIdWithRelations(Long id);

    /**
     * Gets a list of all the news ordered by its creation time.
     *
     * @param desc true - if descending sorting, false - if ascending
     * @param offset a value of needed offset
     * @param limit a value of needed number of news
     * @return a list of all the news ordered by its creation time
     * @throws DataAccessException
     */
    List<News> getOrderedByCreationTime(boolean desc, int offset, int limit);

    /**
     * Gets a count of all the news.
     *
     * @return a count of all the new
     * @throws DataAccessException
     */
    long getCount();

    /**
     * Gets a list of news with the specified tags and authors ordered by its creation time.
     *
     * @param tagIds a collection of ids of specified tags (null if no specified tags)
     * @param authorIds a collection of ids of specified authors (null if no specified authors)
     * @param desc true - if descending sorting, false - if ascending
     * @param offset a value of needed offset
     * @param limit a value of needed number of news
     * @return a list of all the news ordered by its creation time
     * @throws DataAccessException
     */
    List<News> getByTagsAndAuthorsOrderedByCreationTime(Collection<Long> tagIds, Collection<Long> authorIds,
                                                        boolean desc, int offset, int limit);

    /**
     * Gets a count of news with the specified tags and authors.
     *
     * @param tagIds a collection of ids of specified tags (null if no specified tags)
     * @param authorIds a collection of ids of specified authors (null if no specified authors)
     * @return a count of news with the specified tags and authors
     * @throws DataAccessException
     */
    long getCountByTagsAndAuthors(Collection<Long> tagIds, Collection<Long> authorIds);
}
