package com.epam.esm.task1.service.impl;

import com.epam.esm.task1.dao.AuthorDao;
import com.epam.esm.task1.dto.AuthorDto;
import com.epam.esm.task1.dto.converter.impl.AuthorDtoConverter;
import com.epam.esm.task1.entity.Author;
import com.epam.esm.task1.service.AuthorService;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of the AuthorService interface.
 *
 * @author Uladzislau Kastsevich
 */
@Service
@Transactional
public class AuthorServiceImpl extends GenericServiceImpl<Author, AuthorDto, Long> implements AuthorService {
    private AuthorDao authorDao;
    private AuthorDtoConverter authorDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getOrderedByNewsCount(int offset, int limit, boolean desc) {
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<Author> authors = authorDao.getOrderedByNewsCount(offset, limit, desc);
        return authorDtoConverter.getDtoList(authors);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getByNewsId(Long newsId) {
        Assert.notNull(newsId);

        List<Author> authors = authorDao.getByNewsId(newsId);
        return authorDtoConverter.getDtoList(authors);
    }

    @Override
    public List<AuthorDto> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc) {
        Assert.notNull(nameFragment);
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<Author> authors = authorDao.getByNameFragmentOrderedByNewsCount(nameFragment, offset, limit, desc);
        return authorDtoConverter.getDtoList(authors);
    }

    @Override
    public AuthorDto add(AuthorDto authorDto) {
        Assert.notNull(authorDto);
        authorDto.setId(null);
        dtoValidator.validate(authorDto);

        Author authorWithGivenName = authorDao.getByName(authorDto.getName());
        if (authorWithGivenName != null) {
            throw new ServiceValidationException(ServiceValidationException.AUTHOR_NAME_ALREADY_EXIST, "The given author's name already exists");
        }

        Author author = authorDtoConverter.getEntity(authorDto);
        Author addedAuthor = authorDao.add(author);
        return authorDtoConverter.getDto(addedAuthor);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Assert.notNull(authorDto);
        Assert.notNull(authorDto.getId());
        dtoValidator.validate(authorDto);

        Author authorWithGivenId = authorDao.getById(authorDto.getId());
        if (authorWithGivenId == null) {
            throw new EntityNotFoundException();
        }
        Author authorWithGivenName = authorDao.getByName(authorDto.getName());
        if (authorWithGivenName != null && !authorWithGivenName.getId().equals(authorDto.getId())) {
            throw new ServiceValidationException(ServiceValidationException.AUTHOR_NAME_ALREADY_EXIST, "The given author's name already exists");
        }

        Author author = authorDtoConverter.getEntity(authorDto);
        Author updatedAuthor = authorDao.update(author);
        return authorDtoConverter.getDto(updatedAuthor);
    }

    @Autowired
    public void setAuthorDao(AuthorDao authorDao) {
        setGenericDao(authorDao);
        this.authorDao = authorDao;
    }

    @Autowired
    public void setAuthorDtoConverter(AuthorDtoConverter authorDtoConverter) {
        setDtoConverter(authorDtoConverter);
        this.authorDtoConverter = authorDtoConverter;
    }
}
