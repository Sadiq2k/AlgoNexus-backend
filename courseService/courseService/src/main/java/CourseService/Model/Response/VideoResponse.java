package CourseService.Model.Response;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {

    private Long id;
    private String title;
    private String description;
    private String videoName;
    private String videoUrl;
    private String videoId;


}
