package com.TestCaseService.Judge0.error;

public class SandboxError extends RuntimeException{
    public SandboxError(Exception e, String message) {
        super(message,e);
    }

    public SandboxError(Exception e) {
        super(e);
    }
}
