package com.movierecomander.backend.advice;

public class ErrorsAdvice {
    private String errorsType;
    private String errors;
    private String[] errorsSummary;

    public ErrorsAdvice(String errorsType, String errors, String[] errorsSummary) {
        this.errorsType = errorsType;
        this.errors = errors;
        this.errorsSummary = errorsSummary;
    }

    public String getErrorsType() {
        return errorsType;
    }

    public void setErrorsType(String errorsType) {
        this.errorsType = errorsType;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String[] getErrorsSummary() {
        return errorsSummary;
    }

    public void setErrorsSummary(String[] errorsSummary) {
        this.errorsSummary = errorsSummary;
    }
}
