package com.movierecomander.backend.advice;

public class BusinessException extends IllegalStateException {
    private String errorType;

    public BusinessException(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String[] getMessageArray() {
        String[] messageArray = new String[1];
        messageArray[0] = this.getMessage();
        return messageArray;
    }
}
