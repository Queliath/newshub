package com.epam.esm.task1.dto.converter.impl;

import com.epam.esm.task1.dto.CommentDto;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.dto.converter.helper.ZoneOffsetHelper;
import com.epam.esm.task1.entity.Comment;
import com.epam.esm.task1.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DtoConverter interface for the Comment entity.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class CommentDtoConverter implements DtoConverter<Comment, CommentDto> {
    @Autowired
    private ZoneOffsetHelper zoneOffsetHelper;

    @Override
    public CommentDto getDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreationTime(zoneOffsetHelper.getOffsetDateTime(comment.getCreationTime()));
        commentDto.setNewsId(comment.getNewsId());

        return commentDto;
    }

    @Override
    public Comment getEntity(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setCreationTime(zoneOffsetHelper.getLocalDateTime(commentDto.getCreationTime()));
        comment.setNewsId(commentDto.getNewsId());

        return comment;
    }

    @Override
    public List<CommentDto> getDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>(commentList.size());
        for (Comment comment : commentList) {
            commentDtoList.add(getDto(comment));
        }
        return commentDtoList;
    }
}
