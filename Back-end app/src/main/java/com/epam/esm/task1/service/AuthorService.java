package com.epam.esm.task1.service;

import org.springframework.dao.DataAccessException;
import com.epam.esm.task1.dto.AuthorDto;

import java.util.List;

/**
 * Provides the business-logic related to the Author entity.
 *
 * @author Uladzisau Kastsevich
 */
public interface AuthorService extends GenericService<AuthorDto, Long> {
    /**
     * Gets a list of authors ordered by news count.
     *
     * @param desc desc true - if descending sorting, false - if ascending
     * @return a list of authors
     * @throws DataAccessException
     */
    List<AuthorDto> getOrderedByNewsCount(int offset, int limit, boolean desc);

    /**
     * Gets a list of authors related to a certain news.
     *
     * @param newsId an ID of a certain news
     * @return a list of authors
     * @throws DataAccessException
     */
    List<AuthorDto> getByNewsId(Long newsId);

    /**
     * Gets a list of authors by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @param offset a needed offset
     * @param limit a needed limit
     * @return a list of authors
     */
    List<AuthorDto> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc);
}
