package CourseService.Model.Response;

import CourseService.Entities.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddCourseResponse {


    private Course courses;

    private Integer totalVideoCount;
}
