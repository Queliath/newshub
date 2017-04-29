package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.Comment;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Provides the DAO-logic related to the Comment entity.
 *
 * @author Uladzislau Kastsevich
 */
public interface CommentDao extends GenericDao<Comment, Long> {
    /**
     * Gets a list of comments related to the certain news ordered by its creation time.
     *
     * @param newsId an ID of a certain news
     * @param desc desc true - if descending sorting, false - if ascending
     * @param offset a value of needed offset
     * @param limit a value of needed number of news
     * @return a list of comments
     * @throws DataAccessException
     */
    List<Comment> getByNewsIdOrderedByCreationTime(Long newsId, boolean desc, int offset, int limit);

    /**
     * Gets a count of comments related to a certain news.
     *
     * @param newsId an ID of a certain news
     * @return a count of comments
     */
    Long getCountByNewsId(Long newsId);
}
