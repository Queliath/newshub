package com.epam.esm.task1.entity;

import javax.persistence.*;
import java.util.List;

/**
 * POJO class for the Tag entity.
 *
 * @author Uladzislau Kastsevich
 */
@Entity
@Table(name = "nh_tags")
@NamedQueries({
        @NamedQuery(name = "Tag.getAllOrderedByNewsCountAsc", query = "select new Tag(t.id, t.name, count(n.id)) from Tag t left join t.newsList n group by t.id order by count(n.id)"),
        @NamedQuery(name = "Tag.getAllOrderedByNewsCountDesc", query = "select new Tag(t.id, t.name, count(n.id)) from Tag t left join t.newsList n group by t.id order by count(n.id) desc"),
        @NamedQuery(name = "Tag.getByNews", query = "select t from Tag t join t.newsList n where n.id = :newsId"),
        @NamedQuery(name = "Tag.getCountByIdsRange", query = "select count (t.id) from Tag t where t.id in (:tagIds)"),
        @NamedQuery(name = "Tag.getByName", query = "select t from Tag t where t.name = :name"),
        @NamedQuery(name = "Tag.getByNameFragmentOrderedByNewsCountDesc", query = "select new Tag(t.id, t.name, count(n.id)) from Tag t left join t.newsList n where lower(t.name) like lower(:namePattern) group by t.id order by count(n.id) desc"),
        @NamedQuery(name = "Tag.getByNameFragmentOrderedByNewsCountAsc", query = "select new Tag(t.id, t.name, count(n.id)) from Tag t left join t.newsList n where lower(t.name) like lower(:namePattern) group by t.id order by count(n.id)")
})
public class Tag extends EntityObject<Long> {
    public static final String GET_ALL_ORDERED_BY_NEWS_COUNT_ASC = "Tag.getAllOrderedByNewsCountAsc";
    public static final String GET_ALL_ORDERED_BY_NEWS_COUNT_DESC = "Tag.getAllOrderedByNewsCountDesc";
    public static final String GET_BY_NEWS = "Tag.getByNews";
    public static final String GET_COUNT_BY_IDS_RANGE = "Tag.getCountByIdsRange";
    public static final String GET_BY_NAME = "Tag.getByName";
    public static final String GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_DESC = "Tag.getByNameFragmentOrderedByNewsCountDesc";
    public static final String GET_BY_NAME_FRAGMENT_ORDERED_BY_NEWS_COUNT_ASC = "Tag.getByNameFragmentOrderedByNewsCountAsc";

    private String name;

    private List<News> newsList;
    private Long newsCount;

    public Tag() {
    }

    public Tag(Long id, String name, Long newsCount) {
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

    @ManyToMany(mappedBy = "tags")
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
