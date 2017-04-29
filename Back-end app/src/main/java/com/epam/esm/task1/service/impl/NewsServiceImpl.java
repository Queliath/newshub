package com.epam.esm.task1.service.impl;

import com.epam.esm.task1.dao.AuthorDao;
import com.epam.esm.task1.dao.NewsDao;
import com.epam.esm.task1.dao.TagDao;
import com.epam.esm.task1.dto.AuthorDto;
import com.epam.esm.task1.dto.TagDto;
import com.epam.esm.task1.dto.converter.impl.NewsDtoConverter;
import com.epam.esm.task1.entity.News;
import com.epam.esm.task1.dto.NewsDto;
import com.epam.esm.task1.service.NewsService;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of the NewsService interface.
 *
 * @author Uladzislau Kastsevich
 */
@Service
@Transactional
public class NewsServiceImpl extends GenericServiceImpl<News, NewsDto, Long> implements NewsService {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private TagDao tagDao;
    private NewsDao newsDao;
    private NewsDtoConverter newsDtoConverter;

    @Override
    public NewsDto add(NewsDto newsDto) {
        validateDto(newsDto);
        validateAuthorsAndTags(newsDto);

        return convertToEntityAndAdd(newsDto);
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        validateDto(newsDto);

        News newsWithGivenId = newsDao.getById(newsDto.getId());
        if (newsWithGivenId == null) {
            throw new EntityNotFoundException();
        }
        validateAuthorsAndTags(newsDto);

        News news = newsDtoConverter.getEntity(newsDto);
        news.setLastModificationTime(LocalDateTime.now());
        News updatedNews = newsDao.update(news);
        return newsDtoConverter.getDtoWith(updatedNews, true, true, true);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDto getById(Long id) {
        Assert.notNull(id);

        News newsWithGivenId = newsDao.getById(id);
        if (newsWithGivenId == null) {
            throw new EntityNotFoundException();
        }

        News news = newsDao.getByIdWithRelations(id);
        return newsDtoConverter.getDtoWith(news, true, true, true);
    }

    @Override
    public List<NewsDto> addListOfNews(List<NewsDto> listOfNews) {
        Assert.notEmpty(listOfNews);
        for (NewsDto newsDto : listOfNews) {
            validateDto(newsDto);
        }
        for (NewsDto newsDto : listOfNews) {
            validateAuthorsAndTags(newsDto);
        }

        List<NewsDto> addedNewsList = new ArrayList<>(listOfNews.size());
        for (NewsDto newsDto : listOfNews) {
            NewsDto addedNewsDto = convertToEntityAndAdd(newsDto);
            addedNewsList.add(addedNewsDto);
        }
        return addedNewsList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getOrderedByCreationTime(boolean desc, int offset, int limit) {
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<News> newsList = newsDao.getOrderedByCreationTime(desc, offset, limit);
        return newsDtoConverter.getDtoListWith(newsList, false, true, false);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return newsDao.getCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getByTagsAndAuthorsOrderedByCreationTime(Collection<Long> tagIds, Collection<Long> authorIds, boolean desc, int offset, int limit) {
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<News> newsList = newsDao.getByTagsAndAuthorsOrderedByCreationTime(tagIds, authorIds, desc, offset, limit);
        return newsDtoConverter.getDtoListWith(newsList, false, true, false);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountByTagsAndAuthors(Collection<Long> tagIds, Collection<Long> authorIds) {
        return newsDao.getCountByTagsAndAuthors(tagIds, authorIds);
    }

    @Autowired
    public void setNewsDao(NewsDao newsDao) {
        setGenericDao(newsDao);
        this.newsDao = newsDao;
    }

    @Autowired
    public void setNewsDtoConverter(NewsDtoConverter newsDtoConverter) {
        setDtoConverter(newsDtoConverter);
        this.newsDtoConverter = newsDtoConverter;
    }

    private void validateDto(NewsDto newsDto) {
        Assert.notNull(newsDto);
        newsDto.setId(null);
        newsDto.setLastModificationTime(null);
        dtoValidator.validate(newsDto);
    }

    private NewsDto convertToEntityAndAdd(NewsDto newsDto) {
        News news = newsDtoConverter.getEntity(newsDto);
        news.setCreationTime(LocalDateTime.now());
        News addedNews = newsDao.add(news);
        return newsDtoConverter.getDtoWith(addedNews, true, true, true);
    }

    private void validateAuthorsAndTags(NewsDto newsDto) {
        boolean authorsExistence = checkAuthorsExistence(newsDto);
        boolean tagsExistence = checkTagsExistence(newsDto);

        ServiceValidationException sve = new ServiceValidationException();
        if (!authorsExistence) {
            sve.addMessage(ServiceValidationException.AT_LEAST_ONE_AUTHOR_DOES_NOT_EXIST, "At least one author doesn't exist");
        }
        if (!tagsExistence) {
            sve.addMessage(ServiceValidationException.AT_LEAST_ONE_TAG_DOES_NOT_EXIST, "At least one tag doesn't exist");
        }
        if (!sve.getMessages().isEmpty()) {
            throw sve;
        }
    }

    private boolean checkAuthorsExistence(NewsDto newsDto) {
        if (newsDto.getAuthors() != null && !newsDto.getAuthors().isEmpty()) {
            Set<Long> authorIds = new HashSet<>(newsDto.getAuthors().size());
            for (AuthorDto author : newsDto.getAuthors()) {
                authorIds.add(author.getId());
            }

            Long authorsCount = authorDao.getCountByIdsRange(authorIds);
            if (authorsCount.intValue() != authorIds.size()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTagsExistence(NewsDto newsDto) {
        if (newsDto.getTags() != null && !newsDto.getTags().isEmpty()) {
            Set<Long> tagIds = new HashSet<>(newsDto.getTags().size());
            for (TagDto tag : newsDto.getTags()) {
                tagIds.add(tag.getId());
            }

            Long tagsCount = tagDao.getCountByIdsRange(tagIds);
            if (tagsCount.intValue() != tagIds.size()) {
                return false;
            }
        }
        return true;
    }
}
