package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.Author;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * Provides the DAO-logic related to the Author entity.
 *
 * @author Uladzislau Kastsevich
 */
public interface AuthorDao extends GenericDao<Author, Long> {
    /**
     * Gets a list of authors ordered by news count.
     *
     * @param desc desc true - if descending sorting, false - if ascending
     * @return a list of authors
     * @throws DataAccessException
     */
    List<Author> getOrderedByNewsCount(int offset, int limit, boolean desc);

    /**
     * Gets a list of authors related to a specified news.
     *
     * @param newsId an id of a specified news
     * @return a list of authors
     * @throws DataAccessException
     */
    List<Author> getByNewsId(Long newsId);

    /**
     * Gets a unique author by its name.
     *
     * @param name a name of an author
     * @return an instance of Author entity
     */
    Author getByName(String name);

    /**
     * Gets a list of authors by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @return a list of authors
     */
    List<Author> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc);

    /**
     * Gets a count of authors by the given ids range.
     *
     * @param idsRange a collection of ids
     * @return a count of authors
     */
    Long getCountByIdsRange(Collection<Long> idsRange);
}
