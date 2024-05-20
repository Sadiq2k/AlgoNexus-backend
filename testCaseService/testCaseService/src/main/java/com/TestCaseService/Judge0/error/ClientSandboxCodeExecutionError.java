package com.TestCaseService.Judge0.error;

public class ClientSandboxCodeExecutionError extends RuntimeException {
    public ClientSandboxCodeExecutionError(Exception e,String message) {
        super(message,e);
    }
}