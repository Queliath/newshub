package com.epam.esm.task1.service;

import com.epam.esm.task1.dto.NewsDto;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * Provides the business-logic related to the News entity.
 *
 * @author Uladzisau Kastsevich
 */
public interface NewsService extends GenericService<NewsDto, Long> {
    /**
     * Adds a list of news to the system.
     *
     * @param listOfNews a list of news to be added
     * @return an added list of news
     */
    List<NewsDto> addListOfNews(List<NewsDto> listOfNews);

    /**
     * Gets a list of all the news ordered by its creation time.
     *
     * @param desc true - if descending sorting, false - if ascending
     * @param offset a value of needed offset
     * @param limit a value of needed number of news
     * @return a list of all the news ordered by its creation time
     * @throws DataAccessException
     */
    List<NewsDto> getOrderedByCreationTime(boolean desc, int offset, int limit);

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
    List<NewsDto> getByTagsAndAuthorsOrderedByCreationTime(Collection<Long> tagIds, Collection<Long> authorIds,
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
