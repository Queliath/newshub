package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.Tag;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * Provides the DAO-logic related to the Tag entity.
 *
 * @author Uladzislau Kastsevich
 */
public interface TagDao extends GenericDao<Tag, Long> {
    /**
     * Gets a list of tags ordered by news count.
     *
     * @param desc desc true - if descending sorting, false - if ascending
     * @return a list of tags
     * @throws DataAccessException
     */
    List<Tag> getOrderedByNewsCount(int offset, int limit, boolean desc);

    /**
     * Gets a list of tags related to a specified news.
     *
     * @param newsId an id of a specified news
     * @return a list of tags
     * @throws DataAccessException
     */
    List<Tag> getByNewsId(Long newsId);

    /**
     * Gets a unique tag by its name.
     *
     * @param name a name of a tag
     * @return an instance of the Tag entity
     */
    Tag getByName(String name);

    /**
     * Gets a list of tags by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @param offset a needed offset
     * @param limit a needed limit
     * @return a list of tags
     */
    List<Tag> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc);

    /**
     * Gets a count of tags by ids range.
     *
     * @param idsRange a collection of ids
     * @return a count of tags
     */
    Long getCountByIdsRange(Collection<Long> idsRange);
}
