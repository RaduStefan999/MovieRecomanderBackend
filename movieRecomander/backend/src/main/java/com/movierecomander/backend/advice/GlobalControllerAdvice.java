package com.movierecomander.backend.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String[] errorsSummary = exception.getAllErrors().stream().map(err -> err.getDefaultMessage()).toArray(String[]::new);

        return new ResponseEntity<>(new ErrorsAdvice("Validation Error", exception.getBindingResult().toString(),
                errorsSummary), HttpStatus.BAD_REQUEST);
    }
}
