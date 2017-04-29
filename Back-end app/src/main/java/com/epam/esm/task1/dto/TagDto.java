package com.epam.esm.task1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for the Tag entity.
 *
 * @author Uladzislau Kastsevich
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto extends Dto<Long> {
    private static final long serialVersionUID = 154398351539568364L;

    @NotNull(message = "{tag.nameRequired}")
    @Size(min = 1, max = 30, message = "{tag.nameSize}")
    private String name;
    private Long newsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Long newsCount) {
        this.newsCount = newsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagDto tagDto = (TagDto) o;

        if (id != null ? !id.equals(tagDto.id) : tagDto.id != null) {
            return false;
        }
        return !(name != null ? !name.equals(tagDto.name) : tagDto.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
