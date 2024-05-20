package problemsService.Model.Judge0.error;

public class SandboxCodeExecutionError extends RuntimeException {
    public SandboxCodeExecutionError(Exception e,String message) {
        super(message,e);
    }
}