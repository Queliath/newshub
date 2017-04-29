package com.epam.esm.task1.service.impl;

import com.epam.esm.task1.dao.CommentDao;
import com.epam.esm.task1.dao.NewsDao;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.CommentDto;
import com.epam.esm.task1.dto.converter.impl.CommentDtoConverter;
import com.epam.esm.task1.entity.Comment;
import com.epam.esm.task1.entity.News;
import com.epam.esm.task1.service.CommentService;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the CommentService interface.
 *
 * @author Uladzislau Kastsevich
 */
@Service
@Transactional
public class CommentServiceImpl extends GenericServiceImpl<Comment, CommentDto, Long> implements CommentService {
    @Autowired
    private NewsDao newsDao;
    private CommentDao commentDao;
    private CommentDtoConverter commentDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getByNewsIdOrderedByCreationTime(Long newsId, boolean desc, int offset, int limit) {
        Assert.notNull(newsId);
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<Comment> comments = commentDao.getByNewsIdOrderedByCreationTime(newsId, desc, offset, limit);
        return commentDtoConverter.getDtoList(comments);
    }

    @Override
    public Long getCountByNewsId(Long newsId) {
        Assert.notNull(newsId);

        return commentDao.getCountByNewsId(newsId);
    }

    @Override
    public CommentDto add(CommentDto commentDto) {
        Assert.notNull(commentDto);
        commentDto.setId(null);
        dtoValidator.validate(commentDto);

        News news = newsDao.getById(commentDto.getNewsId());
        if (news == null) {
            throw new ServiceValidationException(ServiceValidationException.NEWS_DOES_NOT_EXIST, "There is no news with the given ID");
        }

        Comment comment = commentDtoConverter.getEntity(commentDto);
        comment.setCreationTime(LocalDateTime.now());
        comment.setNews(news);
        Comment addedComment = commentDao.add(comment);
        return commentDtoConverter.getDto(addedComment);
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        Assert.notNull(commentDto);
        Assert.notNull(commentDto.getId());
        commentDto.setCreationTime(null);
        dtoValidator.validate(commentDto);

        Comment commentWithGivenId = commentDao.getById(commentDto.getId());
        if (commentWithGivenId == null) {
            throw new EntityNotFoundException();
        }
        News news = newsDao.getById(commentDto.getNewsId());
        if (news == null) {
            throw new ServiceValidationException(ServiceValidationException.NEWS_DOES_NOT_EXIST, "There is no news with the given ID");
        }

        Comment comment = commentDtoConverter.getEntity(commentDto);
        comment.setNews(news);
        Comment updatedComment = commentDao.update(comment);
        return commentDtoConverter.getDto(updatedComment);
    }

    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        setGenericDao(commentDao);
        this.commentDao = commentDao;
    }

    @Autowired
    public void setCommentDtoConverter(CommentDtoConverter commentDtoConverter) {
        setDtoConverter(commentDtoConverter);
        this.commentDtoConverter = commentDtoConverter;
    }
}
