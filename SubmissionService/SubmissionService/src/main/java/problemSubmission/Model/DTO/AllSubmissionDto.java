package problemSubmission.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllSubmissionDto {

    private LocalDateTime submissionTime;
    private String title;
    private boolean isSolved;
    private Double averageTime;
}
