package com.epam.esm.task1.controller;

import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.TagDto;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

/**
 * Services requests related to the Tag entity.
 *
 * @author Uladzislau Kastsevich
 */
@RestController
@CrossOrigin
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * Services a POST-request and creates a new tag.
     *
     * @param tagDto an instance of the TagDto
     * @param request an instance of the current HttpServletRequest
     * @return CREATED status an the created tag
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     */
    @CrossOrigin(exposedHeaders = "Location")
    @PostMapping
    public ResponseEntity<TagDto> addTag(@RequestBody TagDto tagDto, HttpServletRequest request) {
        TagDto addedTag = tagService.add(tagDto);

        URI uri = ServletUriComponentsBuilder.fromRequest(request).path("/{id}").build().expand(addedTag.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(addedTag);
    }

    /**
     * Services a PUT-request and updates an existing tag.
     *
     * @param id an ID of the tag to be updated
     * @param tagDto an instance of the tagDto
     * @return OK status an the updated tag
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     * @throws EntityNotFoundException when there is no tag with this ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable("id") Long id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        TagDto updatedTag = tagService.update(tagDto);

        return ResponseEntity.ok(updatedTag);
    }

    /**
     * Services a DELETE-request and deletes an existing tag.
     *
     * @param id an ID of the tag to be deleted
     * @return NO CONTENT status code
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no tag with this ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Returns a certain tag by its ID.
     *
     * @param id an ID of the needed tag
     * @return an instance of TagDto
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no tag with this ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable("id") Long id) {
        TagDto tagDto = tagService.getById(id);
        return ResponseEntity.ok(tagDto);
    }

    /**
     * Returns a list of all tags ordered by its news count.
     *
     * @return a list of all tags
     * @throws DataAccessException when there is something wrong during the data accessing
     */
    @GetMapping
    public ResponseEntity<List<TagDto>> get(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        List<TagDto> tagDtoList = tagService.getOrderedByNewsCount(offset, limit, true);
        return ResponseEntity.ok(tagDtoList);
    }

    /**
     * Returns a list of tags by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @param offset a needed offset
     * @param limit a needed limit
     * @return a list of tags
     */
    @GetMapping(params = {"name"})
    public ResponseEntity<List<TagDto>> getByNameFragment(@RequestParam("name") String nameFragment,
                                                          @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                          @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        List<TagDto> tagDtoList = tagService.getByNameFragmentOrderedByNewsCount(nameFragment, offset, limit, true);
        return ResponseEntity.ok(tagDtoList);
    }
}
