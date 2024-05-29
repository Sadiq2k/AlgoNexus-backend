package problemSubmission.Model.Entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import problemSubmission.Model.DTO.AcceptCase;
import problemSubmission.Model.DTO.RejectCase;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Submission")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submissions {

    @Id
    private String submissionId;
    private Long problemNo;
    private String title;
    private String userId;
    private String problemId;
    private String submission;
    private Double averageTime;
    private Double averageMemory;
    private String difficulty;
    private List<RejectCase> rejectedCases;
    private List<AcceptCase> acceptedCases;
    private String sourceCode;
    private LocalDateTime submissionTime;
    private boolean isSolved;

}
