package com.epam.esm.task1.dto.validator.exception;

import java.util.List;

/**
 * Throws when there is some problem with the user's input data.
 *
 * @author Uladzislau Kastsevich
 */
public class DtoValidationException extends RuntimeException {
    private static final long serialVersionUID = 189354203964808239L;

    private List<String> messages;

    public DtoValidationException() {
    }

    public DtoValidationException(List<String> messages) {
        this.messages = messages;
    }

    public DtoValidationException(Throwable cause) {
        super(cause);
    }

    public DtoValidationException(Throwable cause, List<String> messages) {
        super(cause);
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
