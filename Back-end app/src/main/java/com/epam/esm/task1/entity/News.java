package com.epam.esm.task1.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * POJO class for the NewsDto entity.
 *
 * @author Uladzislau Kastsevich
 */
@Entity
@Table(name = "nh_news")
@NamedQueries({
        @NamedQuery(name = "News.getIdsRangeOrderedByCreationTimeAsc", query = "select n.id from News n order by n.creationTime"),
        @NamedQuery(name = "News.getIdsRangeOrderedByCreationTimeDesc", query = "select n.id from News n order by n.creationTime desc"),
        @NamedQuery(name = "News.getCount", query = "select count(n.id) from News n"),
        @NamedQuery(name = "News.getByIdsRangeOrderedByCreationTimeAsc", query = "select distinct n from News n left join fetch n.authors where n.id in (:newsIds) order by n.creationTime"),
        @NamedQuery(name = "News.getByIdsRangeOrderedByCreationTimeDesc", query = "select distinct n from News n left join fetch n.authors where n.id in (:newsIds) order by n.creationTime desc"),
        @NamedQuery(name = "News.getById", query = "select n from News n left join fetch n.authors left join fetch n.tags where n.id = :id"),
        @NamedQuery(name = "News.getCommentsCount", query = "select count(c.id) from News n left join n.comments c where n.id = :id"),
        @NamedQuery(name = "News.getCommentsCountByIdsRange", query = "select new News(n.id, count(c.id)) from News n left join n.comments c where n.id in (:newsIds) group by n")
})
public class News extends EntityObject<Long> {
    public static final String GET_IDS_RANGE_ORDERED_BY_CREATION_TIME_ASC = "News.getIdsRangeOrderedByCreationTimeAsc";
    public static final String GET_IDS_RANGE_ORDERED_BY_CREATION_TIME_DESC = "News.getIdsRangeOrderedByCreationTimeDesc";
    public static final String GET_COUNT = "News.getCount";
    public static final String GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_ASC = "News.getByIdsRangeOrderedByCreationTimeAsc";
    public static final String GET_BY_IDS_RANGE_ORDERED_BY_CREATION_TIME_DESC = "News.getByIdsRangeOrderedByCreationTimeDesc";
    public static final String GET_BY_ID = "News.getById";
    public static final String GET_COMMENTS_COUNT = "News.getCommentsCount";
    public static final String GET_COMMENTS_COUNT_BY_IDS_RANGE = "News.getCommentsCountByIdsRange";

    private String title;
    private String briefContent;
    private String fullContent;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationTime;

    private Set<Comment> comments;
    private Set<Author> authors;
    private Set<Tag> tags;
    private Long commentsCount;

    public News() {
    }

    public News(Long id, Long commentsCount) {
        this.id = id;
        this.commentsCount = commentsCount;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "brief_content")
    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    @Column(name = "full_content")
    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    @Column(name = "creation_time", updatable = false)
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Column(name = "last_modification_time")
    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @OneToMany(mappedBy = "news")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @ManyToMany
    @JoinTable(name = "nh_news_authors", joinColumns = @JoinColumn(name = "news_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @ManyToMany
    @JoinTable(name = "nh_news_tags", joinColumns = @JoinColumn(name = "news_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Transient
    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }
}
