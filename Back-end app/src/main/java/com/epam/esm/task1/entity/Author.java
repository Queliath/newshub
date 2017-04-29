package com.epam.esm.task1.entity;

import javax.persistence.*;
import java.util.List;

/**
 * POJO class for the Author entity.
 *
 * @author Uladzislau Kastsevich
 */
@Entity
@Table(name = "nh_authors")
@NamedQueries({
        @NamedQuery(name = "Author.getAllOrderedByNewsCountAcs", query = "select new Author(a.id, a.name, count(n.id)) from Author a left join a.newsList n group by a.id order by count(n.id)"),
        @NamedQuery(name = "Author.getAllOrderedByNewsCountDesc", query = "select new Author(a.id, a.name, count(n.id)) from Author a left join a.newsList n group by a.id order by count(n.id) desc"),
        @NamedQuery(name = "Author.getByNews", query = "select a from Author a join a.newsList n where n.id = :newsId"),
        @NamedQuery(name = "Author.getCountByIdsRange", query = "select count(a.id) from Author a where a.id in (:authorIds)"),
        @NamedQuery(name = "Author.getByName", query = "select a from Author a where a.name = :name"),
        @NamedQuery(name = "Author.getByNameFragmentOrderedByNewsCountDesc", query = "select new Author(a.id, a.name, count(n.id)) from Author a left join a.newsList n where lower(a.name) like lower(:namePattern) group by a.id order by count(n.id) desc"),
        @NamedQuery(name = "Author.getByNameFragmentOrderedByNewsCountAsc", query = "select new Author(a.id, a.name, count(n.id)) from Author a left join a.newsList n where lower(a.name) like lower(:namePattern) group by a.id order by count(n.id)")
})
public class Author extends EntityObject<Long> {
    public static final String GET_ALL_ORDERED_BY_NEWS_COUNT_ASC = "Author.getAllOrderedByNewsCountAcs";
    public static final String GET_ALL_ORDERED_BY_NEWS_COUNT_DESC = "Author.getAllOrderedByNewsCountDesc";
    public static final String GET_BY_NEWS = "Author.getByNews";
    public static final String GET_COUNT_BY_IDS_RANGE = "Author.getCountByIdsRange";
    public static final String GET_BY_NAME = "Author.getByName";
    public static final String GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_DESC = "Author.getByNameFragmentOrderedByNewsCountDesc";
    public static final String GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_ASC = "Author.getByNameFragmentOrderedByNewsCountAsc";

    private String name;

    private List<News> newsList;
    private Long newsCount;

    public Author() {
    }

    public Author(Long id, String name, Long newsCount) {
        this.id = id;
        this.name = name;
        this.newsCount = newsCount;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "authors")
    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Transient
    public Long getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Long newsCount) {
        this.newsCount = newsCount;
    }
}
