package CourseService.Model.Response;

import CourseService.Entities.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddCourseResponse {

//    private Long courseId;
//    private String topicName;
//    private String description;
//    private String imageName;
//    private String imageUrl;
//    private String imageId;
    private Course courses;

    private Integer totalVideoCount;
}
