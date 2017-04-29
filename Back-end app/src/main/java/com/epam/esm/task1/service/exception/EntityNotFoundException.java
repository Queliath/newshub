package com.epam.esm.task1.service.exception;

/**
 * Throws when there is no entity instance when getting it for example by PK.
 *
 * @author Uladzislau Kastsevich
 */
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 361409968129974272L;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}