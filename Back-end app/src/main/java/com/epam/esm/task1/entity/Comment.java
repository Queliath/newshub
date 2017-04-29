package com.epam.esm.task1.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * POJO class for the Comment entity.
 *
 * @author Uladzislau Kastsevich
 */
@Entity
@Table(name = "nh_comments")
@NamedQueries({
        @NamedQuery(name = "Comment.getByNewsOrderedByCreationTimeAsc", query = "select c from Comment c where c.news.id = :newsId order by c.creationTime"),
        @NamedQuery(name = "Comment.getByNewsOrderedByCreationTimeDesc", query = "select c from Comment c where c.news.id = :newsId order by c.creationTime desc"),
        @NamedQuery(name = "Comment.getCountByNewsId", query = "select count(c.id) from Comment c where c.news.id = :newsId")
})
public class Comment extends EntityObject<Long> {
    public static final String GET_BY_NEWS_ORDERED_BY_CREATION_TIME_ASC = "Comment.getByNewsOrderedByCreationTimeAsc";
    public static final String GET_BY_NEWS_ORDERED_BY_CREATION_TIME_DESC = "Comment.getByNewsOrderedByCreationTimeDesc";
    public static final String GET_COUNT_BY_NEWS_ID = "Comment.getCountByNewsId";

    private String content;
    private LocalDateTime creationTime;

    private News news;
    private Long newsId;

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "creation_time", updatable = false)
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Transient
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
