package com.epam.esm.task1.service.impl;

import com.epam.esm.task1.dao.TagDao;
import com.epam.esm.task1.dto.TagDto;
import com.epam.esm.task1.dto.converter.impl.TagDtoConverter;
import com.epam.esm.task1.entity.Tag;
import com.epam.esm.task1.service.TagService;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of the TagService interface.
 *
 * @author Uladzislau Kastsevich
 */
@Service
@Transactional
public class TagServiceImpl extends GenericServiceImpl<Tag, TagDto, Long> implements TagService {
    private TagDao tagDao;
    private TagDtoConverter tagDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getOrderedByNewsCount(int offset, int limit, boolean desc) {
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<Tag> tags = tagDao.getOrderedByNewsCount(offset, limit, desc);
        return tagDtoConverter.getDtoList(tags);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getByNewsId(Long newsId) {
        Assert.notNull(newsId);

        List<Tag> tags = tagDao.getByNewsId(newsId);
        return tagDtoConverter.getDtoList(tags);
    }

    @Override
    public List<TagDto> getByNameFragmentOrderedByNewsCount(String nameFragment, int offset, int limit, boolean desc) {
        Assert.notNull(nameFragment);
        Assert.isTrue(offset >= 0);
        Assert.isTrue(limit > 0);

        List<Tag> tags = tagDao.getByNameFragmentOrderedByNewsCount(nameFragment, offset, limit, desc);
        return tagDtoConverter.getDtoList(tags);
    }

    @Override
    public TagDto add(TagDto tagDto) {
        Assert.notNull(tagDto);
        tagDto.setId(null);
        dtoValidator.validate(tagDto);

        Tag tagWithGivenName = tagDao.getByName(tagDto.getName());
        if (tagWithGivenName != null) {
            throw new ServiceValidationException(ServiceValidationException.TAG_NAME_ALREADY_EXIST, "The given tag's name already exists");
        }

        Tag tag = tagDtoConverter.getEntity(tagDto);
        Tag addedTag = tagDao.add(tag);
        return tagDtoConverter.getDto(addedTag);
    }

    @Override
    public TagDto update(TagDto tagDto) {
        Assert.notNull(tagDto);
        Assert.notNull(tagDto.getId());
        dtoValidator.validate(tagDto);

        Tag tagWithGivenId = tagDao.getById(tagDto.getId());
        if (tagWithGivenId == null) {
            throw new EntityNotFoundException();
        }
        Tag tagWIthGivenName = tagDao.getByName(tagDto.getName());
        if (tagWIthGivenName != null && !tagWIthGivenName.getId().equals(tagDto.getId())) {
            throw new ServiceValidationException(ServiceValidationException.TAG_NAME_ALREADY_EXIST, "The given tag's name already exists");
        }

        Tag tag = tagDtoConverter.getEntity(tagDto);
        Tag updatedTag = tagDao.update(tag);
        return tagDtoConverter.getDto(updatedTag);
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        setGenericDao(tagDao);
        this.tagDao = tagDao;
    }

    @Autowired
    public void setTagDtoConverter(TagDtoConverter tagDtoConverter) {
        setDtoConverter(tagDtoConverter);
        this.tagDtoConverter = tagDtoConverter;
    }
}
