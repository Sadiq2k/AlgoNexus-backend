package com.TestCaseService.Judge0;

public class TestCaseTimeOutException extends RuntimeException {
    public TestCaseTimeOutException(String message,Exception e) {
        super(message,e);
    }
    public TestCaseTimeOutException(String message) {
        super(message);
    }
}
