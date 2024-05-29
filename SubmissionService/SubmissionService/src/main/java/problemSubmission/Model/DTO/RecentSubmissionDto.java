package problemSubmission.Model.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentSubmissionDto {

    private Long problemNo;
    private String title;
    private LocalDateTime createdAt;
}
