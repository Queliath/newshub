package com.epam.esm.task1.dto.validator;

import com.epam.esm.task1.dto.Dto;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;

/**
 * Validates instances of the DTO classes.
 *
 * @author Uladzislau Kastsevich
 */
public interface DtoValidator {
    /**
     * Validates a given dto object.
     *
     * @param dto an instance of the DTO class
     * @throws DtoValidationException
     */
    void validate(Dto dto);
}
