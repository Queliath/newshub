package com.epam.esm.task1.service.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Throws when the Service-layer validation fails.
 *
 * @author Uladzislau Kastsevich
 */
public class ServiceValidationException extends RuntimeException {
    public static final Integer AUTHOR_NAME_ALREADY_EXIST = 400_01;
    public static final Integer TAG_NAME_ALREADY_EXIST = 400_02;
    public static final Integer NEWS_DOES_NOT_EXIST = 400_03;
    public static final Integer AT_LEAST_ONE_AUTHOR_DOES_NOT_EXIST = 400_04;
    public static final Integer AT_LEAST_ONE_TAG_DOES_NOT_EXIST = 400_05;

    private static final long serialVersionUID = 5416710224891889234L;

    private Map<Integer, String> messages = new HashMap<>();

    public ServiceValidationException() {
    }

    public ServiceValidationException(Throwable cause) {
        super(cause);
    }

    public ServiceValidationException(Integer code, String message) {
        messages.put(code, message);
    }

    public ServiceValidationException(Integer code, String message, Throwable cause) {
        super(cause);
        messages.put(code, message);
    }

    public Map<Integer, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<Integer, String> messages) {
        this.messages = messages;
    }

    public void addMessage(Integer code, String message) {
        messages.put(code, message);
    }
}
