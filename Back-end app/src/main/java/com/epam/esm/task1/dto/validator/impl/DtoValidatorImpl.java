package com.epam.esm.task1.dto.validator.impl;

import com.epam.esm.task1.dto.Dto;
import com.epam.esm.task1.dto.validator.DtoValidator;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the DtoValidator interface.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class DtoValidatorImpl implements DtoValidator {
    private static Logger log = LogManager.getLogger(DtoValidatorImpl.class);

    @Autowired
    private Validator validator;

    @Override
    public void validate(Dto dto) {
        Set<ConstraintViolation<Dto>> constraintViolations = validator.validate(dto);
        if (!constraintViolations.isEmpty()) {
            List<String> validationExceptionMessages = new ArrayList<>(constraintViolations.size());
            for (ConstraintViolation<Dto> constraintViolation : constraintViolations) {
                validationExceptionMessages.add(constraintViolation.getMessage());
            }
            log.error("Error while validating a DTO-object");
            throw new DtoValidationException(validationExceptionMessages);
        }
    }
}
