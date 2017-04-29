package com.epam.esm.task1.controller;

import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.AuthorDto;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.AuthorService;
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
 * Services requests related to the Author entity.
 *
 * @author Uladzislau Kastsevich
 */
@RestController
@CrossOrigin
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    /**
     * Services a POST-request and adds an author to the system.
     *
     * @param authorDto an author to be added
     * @param request an instance of the current HttpServletRequest
     * @return the CREATED status code with the URI of the created news
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     */
    @CrossOrigin(exposedHeaders = "Location")
    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto, HttpServletRequest request) {
        AuthorDto addedAuthor = authorService.add(authorDto);

        URI uri = ServletUriComponentsBuilder.fromRequest(request).path("/{id}").build().expand(addedAuthor.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(addedAuthor);
    }

    /**
     * Services a PUT-request and updates an existing author in the system.
     *
     * @param id an ID of the author to be updated
     * @param authorDto an instance of the AuthorDto
     * @return the OK status code with updated author
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws DtoValidationException when there is problems with the user's input
     * @throws EntityNotFoundException when there is no author with this ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorDto updatedAuthor = authorService.update(authorDto);

        return ResponseEntity.ok(updatedAuthor);
    }

    /**
     * Services a DELETE-request and deletes an existing author from the system.
     *
     * @param id an ID of the author to be deleted
     * @return NO CONTENT status code
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no author with this ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Returns an existing author by its ID.
     *
     * @param id an ID of a needed author
     * @return OK status code and a needed author
     * @throws DataAccessException when there is something wrong during the data accessing
     * @throws EntityNotFoundException when there is no author with this ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        AuthorDto authorDto = authorService.getById(id);
        return ResponseEntity.ok(authorDto);
    }

    /**
     * Returns a list of all authors ordered by its news count desc.
     *
     * @return a list of all authors
     * @throws DataAccessException when there is something wrong during the data accessing
     */
    @GetMapping
    public ResponseEntity<List<AuthorDto>> get(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        List<AuthorDto> authorDtoList = authorService.getOrderedByNewsCount(offset, limit, true);
        return ResponseEntity.ok(authorDtoList);
    }

    /**
     * Returns a list of authors by a fragment of a name.
     *
     * @param nameFragment a fragment of a name
     * @param offset a needed offset
     * @param limit a needed limit
     * @return a list of authors
     */
    @GetMapping(params = {"name"})
    public ResponseEntity<List<AuthorDto>> getByNameFragment(@RequestParam("name") String nameFragment,
                                                             @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                             @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        List<AuthorDto> authorDtoList = authorService.getByNameFragmentOrderedByNewsCount(nameFragment, offset, limit, true);
        return ResponseEntity.ok(authorDtoList);
    }
}
