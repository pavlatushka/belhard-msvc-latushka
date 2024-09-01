package by.latushka.songservice.controller;

import by.latushka.songservice.dto.ApiError;
import by.latushka.songservice.exception.SongNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    private static final String VALIDATION_ERROR = "Song metadata missing validation error";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ApiError> handleSongNotFoundException() {
        return new ResponseEntity<>(new ApiError("The song metadata with the specified id does not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException() {
        return new ResponseEntity<>(new ApiError("An internal server error has occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
