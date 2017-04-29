package com.epam.esm.task1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO class for the News entity.
 *
 * @author Uladzislau Kastsevich
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDto extends Dto<Long> {
    private static final long serialVersionUID = -9097293406318778804L;

    @NotNull(message = "{news.titleRequired}")
    @Size(min = 1, max = 100, message = "{news.titleSize}")
    private String title;

    @NotNull(message = "{news.briefContentRequired}")
    @Size(min = 1, message = "{news.briefContentSize}")
    private String briefContent;

    @NotNull(message = "{news.fullContentRequired}")
    @Size(min = 1, message = "{news.fullContentSize}")
    private String fullContent;

    private OffsetDateTime creationTime;
    private OffsetDateTime lastModificationTime;

    @NotEmpty(message = "{news.authorRequired}")
    private List<AuthorDto> authors = new ArrayList<>();
    private List<TagDto> tags = new ArrayList<>();
    private Long commentCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OffsetDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(OffsetDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewsDto newsDto = (NewsDto) o;

        if (id != null ? !id.equals(newsDto.id) : newsDto.id != null) {
            return false;
        }
        if (title != null ? !title.equals(newsDto.title) : newsDto.title != null) {
            return false;
        }
        if (briefContent != null ? !briefContent.equals(newsDto.briefContent) : newsDto.briefContent != null) {
            return false;
        }
        if (fullContent != null ? !fullContent.equals(newsDto.fullContent) : newsDto.fullContent != null) {
            return false;
        }
        if (creationTime != null ? !creationTime.equals(newsDto.creationTime) : newsDto.creationTime != null) {
            return false;
        }
        return !(lastModificationTime != null ? !lastModificationTime.equals(newsDto.lastModificationTime) : newsDto.lastModificationTime != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (briefContent != null ? briefContent.hashCode() : 0);
        result = 31 * result + (fullContent != null ? fullContent.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (lastModificationTime != null ? lastModificationTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", briefContent='" + briefContent + '\'' +
                ", fullContent='" + fullContent + '\'' +
                ", creationTime=" + creationTime +
                ", lastModificationTime=" + lastModificationTime +
                '}';
    }
}
