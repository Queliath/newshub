package com.epam.esm.task1.controller;

import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.CommentDto;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

/**
 * Services requests related to the Comment entity.
 *
 * @author Uladzislau Kastsevich
 */
@RestController
@CrossOrigin
@RequestMapping("/news/{newsId}/comments")
public class CommentController {
    private static final String TOTAL_COUNT_HEADER_NAME = "X-Total-Count";

    @Autowired
    private CommentService commentService;

    /**
     * Returns a list of comments related to a certain news ordered by its creation time desc.
     *
     * @param newsId an ID of a certain news
     * @return a list of comments related to a certain news
     * @throws DataAccessException when there is something wrong during the data accessing
     */
    @CrossOrigin(exposedHeaders = "X-Total-Count")
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByNewsId(@PathVariable("newsId") Long newsId,
                                                                @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        Long commentCount = commentService.getCountByNewsId(newsId);
        List<CommentDto> commentDtoList = commentService.getByNewsIdOrderedByCreationTime(newsId, true, offset, limit);
        return ResponseEntity.ok().header(TOTAL_COUNT_HEADER_NAME, commentCount.toString()).body(commentDtoList);
    }

    /**
     * Services a POST-request and creates a new comment.
     *
     * @param newsId an ID of a certain news
     * @param commentDto an instance of the CommentDto
     * @param request an instance of the current HttpServletRequest
     * @return CREATED status and a created comment
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     */
    @CrossOrigin(exposedHeaders = "Location")
    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable("newsId") Long newsId, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        commentDto.setNewsId(newsId);
        CommentDto addedComment = commentService.add(commentDto);

        URI uri = ServletUriComponentsBuilder.fromRequest(request).path("/{id}").build().expand(addedComment.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(addedComment);
    }

    /**
     * Services a PUT-request and updates an existing comment.
     *
     * @param newsId an ID of a news which comment related to
     * @param id an ID of the comment to be updated
     * @param commentDto an instance of the CommentDto
     * @return OK status and updated comment
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     * @throws EntityNotFoundException when there is no news of comment with given ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("newsId") Long newsId, @PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        commentDto.setNewsId(newsId);
        commentDto.setId(id);
        CommentDto updatedComment = commentService.update(commentDto);

        return ResponseEntity.ok(updatedComment);
    }

    /**
     * Services a DELETE-request and deletes an existing comment.
     *
     * @param id an ID of the comment to be deleted
     * @return NO CONTENT status
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no news of comment with given ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Returns a certain comment by its ID.
     *
     * @param id an ID of the needed comment
     * @return an instance of the CommentDto
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no news of comment with given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        CommentDto commentDto = commentService.getById(id);
        return ResponseEntity.ok(commentDto);
    }
}
