package com.epam.esm.task1.controller.advice;

import com.epam.esm.task1.dto.ExceptionDto;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides a logic of handling exceptions which are raised during the requests handling.
 *
 * @author Uladzislau Kastsevich
 */
@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {
    private static final String DATA_ACCESS_EXCEPTION_MESSAGE = "There was some problem with the accessing the data storage";
    private static final String BAD_REQUEST_EXCEPTION_MESSAGE = "There was some problem with the user's input data";
    private static final String LACK_OF_RESOURCE_EXCEPTION_MESSAGE = "There is no resource according to your request";
    private static final String UNSUPPORTED_MEDIA_TYPE_EXCEPTION_MESSAGE = "The server doesn't support this media type";
    private static final String NOT_ACCEPTABLE_EXCEPTION_MESSAGE = "You don't support the media type which server produces";
    private static final String METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE = "This http-method is not supported for the given URI";
    private static final String NO_HANDLER_METHOD_EXCEPTION_MESSAGE = "There is no handler for the given URI";
    private static final String UNEXPECTED_EXCEPTION_DEFAULT_MESSAGE = "There is some unexpected problem during the request";

    private static Logger log = LogManager.getLogger(ExceptionHandlingControllerAdvice.class);

    /**
     * Handles the DataAccessException.
     *
     * @param e an instance of the DataAccessException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionDto> handleDataAccessException(DataAccessException e) {
        log.error("Sending the HTTP status code 500 because of DataAccessException", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), DATA_ACCESS_EXCEPTION_MESSAGE));
    }

    /**
     * Handles the lack of resource exceptions.
     *
     * @param e an instance of the exception
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler({EntityNotFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionDto> handleLackOfResourceException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(HttpStatus.NOT_FOUND.value(), LACK_OF_RESOURCE_EXCEPTION_MESSAGE));
    }

    /**
     * Handles the DtoValidationException.
     *
     * @param e an instance of the DtoValidationException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(DtoValidationException.class)
    public ResponseEntity<List<ExceptionDto>> handleDtoValidationException(DtoValidationException e) {
        log.info("Sending the HTTP status code 400 because of DtoValidationException", e);
        List<ExceptionDto> exceptionDtoList = new ArrayList<>(e.getMessages().size());
        for (String errorMessage : e.getMessages()) {
            exceptionDtoList.add(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMessage));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDtoList);
    }

    /**
     * Handles the ServiceValidationException.
     *
     * @param e an instance of the ServiceValidationException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<List<ExceptionDto>> handleServiceValidationException(ServiceValidationException e) {
        log.info("Sending the HTTP status code 400 because of ServiceValidationException", e);
        List<ExceptionDto> exceptionDtoList = new ArrayList<>(e.getMessages().size());
        for (Map.Entry<Integer, String> message : e.getMessages().entrySet()) {
            exceptionDtoList.add(new ExceptionDto(message.getKey(), message.getValue()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDtoList);
    }

    /**
     * Handles the bad request exceptions.
     *
     * @param e an instance of the exception
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler({IllegalArgumentException.class, BindException.class,
            HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDto> handleBadRequestException(Exception e) {
        log.info("Sending the HTTP status code 400 because of bad request", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_EXCEPTION_MESSAGE));
    }

    /**
     * Handles the HttpMediaTypeNotSupportedException.
     *
     * @param e an instance of the HttpMediaTypeNotSupportedException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.info("Sending the HTTP status code 415 because of HttpMediaTypeNotSupportedException", e);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ExceptionDto(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), UNSUPPORTED_MEDIA_TYPE_EXCEPTION_MESSAGE));
    }

    /**
     * Handles the HttpMediaTypeNotAcceptableException.
     *
     * @param e an instance of the HttpMediaTypeNotAcceptableException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        log.info("Sending the HTTP status code 406 because of HttpMediaTypeNotAcceptableException", e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(NOT_ACCEPTABLE_EXCEPTION_MESSAGE);
    }

    /**
     * Handles the HttpRequestMethodNotSupportedException.
     *
     * @param e an instance of the HttpRequestMethodNotSupportedException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("Sending the HTTP status code 405 because of HttpRequestMethodNotSupportedException", e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ExceptionDto(HttpStatus.METHOD_NOT_ALLOWED.value(), METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE));
    }

    /**
     * Handles the NoHandlerFoundException.
     *
     * @param e an instance of the NoHandlerFoundException
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(HttpStatus.NOT_FOUND.value(), NO_HANDLER_METHOD_EXCEPTION_MESSAGE));
    }

    /**
     * Default handler for the unexpected exceptions.
     *
     * @param e an instance of the unexpected exception
     * @return a response with the message with explanations of the error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> defaultHandleException(Exception e) {
        log.error("Sending the HTTP status code 500 because of unexpected exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_EXCEPTION_DEFAULT_MESSAGE));
    }
}
