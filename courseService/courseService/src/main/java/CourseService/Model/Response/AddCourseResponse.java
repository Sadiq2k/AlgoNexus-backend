package CourseService.Model.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AddCourseResponse {

    private Long courseId;
    private String topicName;
    private String description;
    private String imageName;
    private String imageUrl;
    private String imageId;
}
