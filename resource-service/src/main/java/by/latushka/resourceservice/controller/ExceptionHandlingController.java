package by.latushka.resourceservice.controller;

import by.latushka.resourceservice.dto.ApiError;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, InvalidResourceException.class})
    public ResponseEntity<ApiError> handleInvalidResourceException() {
        return new ResponseEntity<>(new ApiError("Validation failed or request body is invalid MP3"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException() {
        return new ResponseEntity<>(new ApiError("The resource with the specified id does not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException() {
        return new ResponseEntity<>(new ApiError("An internal server error has occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
