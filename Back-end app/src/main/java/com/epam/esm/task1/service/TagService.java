package com.epam.esm.task1.service;

import org.springframework.dao.DataAccessException;
import com.epam.esm.task1.dto.TagDto;

import java.util.List;

/**
 * Provides the business-logic related to the Tag entity.
 *
 * @author Uladzisau Kastsevich
 */
public interface TagService extends GenericService<TagDto, Long> {
    /**
     * Gets a list of tags ordered by news count.
     *
     * @param desc desc true - if descending sorting, false - if ascending
     * @return a list of tags
     * @throws DataAccessException
     */
    List<TagDto> getOrderedByNewsCount(int offset, int limit, boolean desc);

    /**
     * Gets a list of tags related to a certain news.
     *
     * @param newsId an ID of a certain news
     * @return a list of tags
     * @throws DataAccessException
     */
    List<TagDto> getByNewsId(Long newsId);

    /**
     * Gets a list of tags by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @param offset a needed offset
     * @param limit a needed limit
     * @return a list of tags
     */
    List<TagDto> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc);
}
