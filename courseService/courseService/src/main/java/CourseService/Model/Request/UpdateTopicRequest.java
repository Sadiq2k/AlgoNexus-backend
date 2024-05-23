package CourseService.Model.Request;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTopicRequest {

    private Long courseId;
    private String topicName;
    @Column(length = 40000)
    private String description;
    private MultipartFile file;

}
