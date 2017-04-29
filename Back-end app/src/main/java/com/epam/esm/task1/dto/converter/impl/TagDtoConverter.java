package com.epam.esm.task1.dto.converter.impl;

import com.epam.esm.task1.dto.TagDto;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DtoConverter interface for the Tag entity.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class TagDtoConverter implements DtoConverter<Tag, TagDto> {
    @Override
    public TagDto getDto(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        tagDto.setNewsCount(tag.getNewsCount());

        return tagDto;
    }

    @Override
    public Tag getEntity(TagDto tagDto) {
        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());

        return tag;
    }

    @Override
    public List<TagDto> getDtoList(List<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>(tagList.size());
        for (Tag tag : tagList) {
            tagDtoList.add(getDto(tag));
        }
        return tagDtoList;
    }
}
