package com.epam.esm.task1.dto.converter.impl;

import com.epam.esm.task1.dto.AuthorDto;
import com.epam.esm.task1.dto.TagDto;
import com.epam.esm.task1.dto.converter.helper.ZoneOffsetHelper;
import com.epam.esm.task1.entity.Author;
import com.epam.esm.task1.entity.News;
import com.epam.esm.task1.dto.NewsDto;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the DtoConverter interface for the News entity.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class NewsDtoConverter implements DtoConverter<News, NewsDto> {
    @Autowired
    private AuthorDtoConverter authorDtoConverter;
    @Autowired
    private TagDtoConverter tagDtoConverter;
    @Autowired
    private ZoneOffsetHelper zoneOffsetHelper;

    @Override
    public NewsDto getDto(News news) {
        NewsDto newsDto = new NewsDto();

        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setBriefContent(news.getBriefContent());
        newsDto.setCreationTime(zoneOffsetHelper.getOffsetDateTime(news.getCreationTime()));
        newsDto.setLastModificationTime(zoneOffsetHelper.getOffsetDateTime(news.getLastModificationTime()));
        newsDto.setCommentCount(news.getCommentsCount());

        return newsDto;
    }

    @Override
    public News getEntity(NewsDto newsDto) {
        News news = new News();

        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setBriefContent(newsDto.getBriefContent());
        news.setFullContent(newsDto.getFullContent());

        if (!newsDto.getAuthors().isEmpty()) {
            Set<Author> authors = new HashSet<>(newsDto.getAuthors().size());
            for (AuthorDto authorDto : newsDto.getAuthors()) {
                authors.add(authorDtoConverter.getEntity(authorDto));
            }
            news.setAuthors(authors);
        }
        if (!newsDto.getTags().isEmpty()) {
            Set<Tag> tags = new HashSet<>(newsDto.getTags().size());
            for (TagDto tagDto : newsDto.getTags()) {
                tags.add(tagDtoConverter.getEntity(tagDto));
            }
            news.setTags(tags);
        }

        return news;
    }

    @Override
    public List<NewsDto> getDtoList(List<News> newsList) {
        List<NewsDto> newsDtoList = new ArrayList<>(newsList.size());
        for (News news : newsList) {
            newsDtoList.add(getDto(news));
        }
        return newsDtoList;
    }

    public NewsDto getDtoWith(News news, boolean withFullContent, boolean withAuthors, boolean withTags) {
        NewsDto newsDto = getDto(news);

        if (withFullContent) {
            newsDto.setFullContent(news.getFullContent());
        }
        if (withAuthors && news.getAuthors() != null && !news.getAuthors().isEmpty()) {
            List<AuthorDto> authorDtoList = new ArrayList<>(news.getAuthors().size());
            for (Author author : news.getAuthors()) {
                authorDtoList.add(authorDtoConverter.getDto(author));
            }
            newsDto.setAuthors(authorDtoList);
        }
        if (withTags && news.getTags() != null && !news.getTags().isEmpty()) {
            List<TagDto> tagDtoList = new ArrayList<>(news.getTags().size());
            for (Tag tag : news.getTags()) {
                tagDtoList.add(tagDtoConverter.getDto(tag));
            }
            newsDto.setTags(tagDtoList);
        }

        return newsDto;
    }

    public List<NewsDto> getDtoListWith(List<News> newsList, boolean withFullContent, boolean withAuthors, boolean withTags) {
        List<NewsDto> newsDtoList = new ArrayList<>(newsList.size());
        for (News news : newsList) {
            newsDtoList.add(getDtoWith(news, withFullContent, withAuthors, withTags));
        }
        return newsDtoList;
    }
}
