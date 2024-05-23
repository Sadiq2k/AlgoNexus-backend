package CourseService.Service;

import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    void addCourse(AddCourseRequest addCourseRequest);

    ResponseEntity<List<AddCourseResponse>> getAllCourserTopic();

    void deleteCourseTopic(Long courseId);

    void updateTopic(UpdateTopicRequest addCourseRequest);
}
