package com.epam.esm.task1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * DTO class for the Comment entity.
 *
 * @author Uladzislau Kastsevich
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto extends Dto<Long> {
    private static final long serialVersionUID = -6591529583863284248L;

    @NotNull(message = "{comment.contentRequired}")
    @Size(min = 1, message = "{comment.contentSize}")
    private String content;
    private OffsetDateTime creationTime;
    @NotNull(message = "{comment.newsIdRequired}")
    private Long newsId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDto commentDto = (CommentDto) o;

        if (id != null ? !id.equals(commentDto.id) : commentDto.id != null) {
            return false;
        }
        if (content != null ? !content.equals(commentDto.content) : commentDto.content != null) {
            return false;
        }
        return !(creationTime != null ? !creationTime.equals(commentDto.creationTime) : commentDto.creationTime != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
