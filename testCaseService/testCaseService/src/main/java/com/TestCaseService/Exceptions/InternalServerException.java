package com.TestCaseService.Exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message, Exception e) {
        super(message,e);
    }
}
