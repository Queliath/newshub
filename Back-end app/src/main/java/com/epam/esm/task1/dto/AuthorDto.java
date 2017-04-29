package com.epam.esm.task1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for the Author entity.
 *
 * @author Uladzislau Kastsevich
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDto extends Dto<Long> {
    private static final long serialVersionUID = -1093073555735822044L;

    @NotNull(message = "{author.nameRequired}")
    @Size(min = 1, max = 50, message = "{author.nameSize}")
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

        AuthorDto authorDto = (AuthorDto) o;

        if (id != null ? !id.equals(authorDto.id) : authorDto.id != null) {
            return false;
        }
        return !(name != null ? !name.equals(authorDto.name) : authorDto.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
