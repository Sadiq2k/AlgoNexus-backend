package problemsService.Model.Judge0.error;

public class SandboxError extends RuntimeException{
    public SandboxError(Exception e, String message) {
        super(message,e);
    }

    public SandboxError(Exception e) {
        super(e);
    }
    public SandboxError(String e) {
        super(e);
    }

}
