package com.movierecommender.backend.advice;

import org.springframework.http.HttpStatus;

public class BusinessException extends IllegalStateException {
    private String errorType;
    private HttpStatus httpStatus;

    public BusinessException(String message, String errorType, HttpStatus httpStatus) {
        super(message);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String[] getMessageArray() {
        String[] messageArray = new String[1];
        messageArray[0] = this.getMessage();
        return messageArray;
    }
}
