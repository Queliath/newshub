package com.epam.esm.task1.controller;

import com.epam.esm.task1.dto.NewsDto;
import org.springframework.dao.DataAccessException;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Services requests related to the News entity.
 *
 * @author Uladzislau Kastsevich
 */
@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {
    private static final String TOTAL_COUNT_HEADER_NAME = "X-Total-Count";

    @Autowired
    private NewsService newsService;

    /**
     * Services a POST-request and adds a news to the system.
     *
     * @param news a news to be added
     * @return the CREATED status code with the URI of the created news
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     */
    @CrossOrigin(exposedHeaders = "Location")
    @PostMapping
    public ResponseEntity<NewsDto> addNews(@RequestBody NewsDto news, HttpServletRequest request) {
        NewsDto addedNews = newsService.add(news);

        URI uri = ServletUriComponentsBuilder.fromRequest(request).path("/{id}").build().expand(addedNews.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(addedNews);
    }

    /**
     * Services a PUT-request and updates an existing news in the system.
     *
     * @param id an id of the updating news (which is the path variable)
     * @param news a news to be updated
     * @return the OK status code
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     * @throws EntityNotFoundException when there is no news with given id
     */
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long id, @RequestBody NewsDto news) {
        news.setId(id);
        NewsDto updatedNews = newsService.update(news);

        return ResponseEntity.ok(updatedNews);
    }

    /**
     * Services a DELETE-request and deletes an existing news from the system.
     *
     * @param id an id of the news to be deleted (which is the path variable)
     * @return the OK status code
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no news with given id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.delete(id);
        
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns a news instance by its ID.
     *
     * @param id an id of the needed news
     * @return an instance of the NewsDto class
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no news with given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        NewsDto newsDto = newsService.getById(id);
        return ResponseEntity.ok(newsDto);
    }

    /**
     * Returns a list of news in the system ordered by its creation time desc.
     *
     * @param offset a needed offset
     * @param limit a needed limit
     * @param tags collection of IDs of the needed tags
     * @param authors collection of IDs of the needed authors
     * @return a list of the news
     * @throws DataAccessException when there is something wrong during the data accessing
     */
    @CrossOrigin(exposedHeaders = "X-Total-Count")
    @GetMapping
    public ResponseEntity<List<NewsDto>> getNews(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
                                                 @RequestParam(value = "tag", required = false) Set<Long> tags,
                                                 @RequestParam(value = "author", required = false) Set<Long> authors) {
        Long newsCount;
        List<NewsDto> newsDtoList;
        if (tags == null && authors == null) {
            newsCount = newsService.getCount();
            newsDtoList = newsService.getOrderedByCreationTime(true, offset, limit);
        } else {
            newsCount = newsService.getCountByTagsAndAuthors(tags, authors);
            newsDtoList = newsService.getByTagsAndAuthorsOrderedByCreationTime(tags, authors, true, offset, limit);
        }

        return ResponseEntity.ok().header(TOTAL_COUNT_HEADER_NAME, newsCount.toString()).body(newsDtoList);
    }


}
