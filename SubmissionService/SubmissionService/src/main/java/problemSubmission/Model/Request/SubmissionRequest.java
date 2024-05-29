package problemSubmission.Model.Request;

import lombok.Getter;
import lombok.Setter;
import problemSubmission.Model.DTO.AcceptCase;
import problemSubmission.Model.DTO.RejectCase;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SubmissionRequest {

    private String userId;
    private Long problemNo;
    private String title;
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
