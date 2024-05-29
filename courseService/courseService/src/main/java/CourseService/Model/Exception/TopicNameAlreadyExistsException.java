package CourseService.Model.Exception;

public class TopicNameAlreadyExistsException extends RuntimeException {
    public TopicNameAlreadyExistsException(String message) {

        super(message);
    }
}
