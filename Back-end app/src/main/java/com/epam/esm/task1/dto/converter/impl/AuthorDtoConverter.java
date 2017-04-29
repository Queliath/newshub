package com.epam.esm.task1.dto.converter.impl;

import com.epam.esm.task1.dto.AuthorDto;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.entity.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DtoConverter interface for the Author entity.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class AuthorDtoConverter implements DtoConverter<Author, AuthorDto> {
    @Override
    public AuthorDto getDto(Author author) {
        AuthorDto authorDto = new AuthorDto();

        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setNewsCount(author.getNewsCount());

        return authorDto;
    }

    @Override
    public Author getEntity(AuthorDto authorDto) {
        Author author = new Author();

        author.setId(authorDto.getId());
        author.setName(authorDto.getName());

        return author;
    }

    @Override
    public List<AuthorDto> getDtoList(List<Author> authorList) {
        List<AuthorDto> authorDtoList = new ArrayList<>(authorList.size());
        for (Author author : authorList) {
            authorDtoList.add(getDto(author));
        }
        return authorDtoList;
    }
}
