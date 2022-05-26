package com.movierecommender.backend.advice;

import org.springframework.http.HttpStatus;

public class BusinessException extends IllegalStateException {
    private final String errorType;
    private final HttpStatus httpStatus;

    public BusinessException(String message, String errorType, HttpStatus httpStatus) {
        super(message);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String[] getMessageArray() {
        String[] messageArray = new String[1];
        messageArray[0] = this.getMessage();
        return messageArray;
    }
}
