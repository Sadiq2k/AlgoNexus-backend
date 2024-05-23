package CourseService.Model.Request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddCourseRequest {

    private String topicName;
    @Column(length = 40000)
    private String description;
    private MultipartFile file;


}
