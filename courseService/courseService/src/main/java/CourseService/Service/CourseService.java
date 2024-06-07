package CourseService.Service;

import CourseService.Entities.Course;
import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;


public interface CourseService {
    void addCourse(AddCourseRequest addCourseRequest);

    ResponseEntity<Page<AddCourseResponse>> getAllCourserTopic(Integer page, Integer size);

    void deleteCourseTopic(Long courseId) throws IOException;

    Course updateTopic(UpdateTopicRequest addCourseRequest) throws IOException;


    Long getTotalCourse();

    List<AddCourseResponse> getLatestCourseTopic();

}
