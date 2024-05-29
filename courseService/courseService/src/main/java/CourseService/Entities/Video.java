package CourseService.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @Size(min = 10 , max = 5000)
    private String description;
    private String videoName;
    private String videoUrl;
    private String videoId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
