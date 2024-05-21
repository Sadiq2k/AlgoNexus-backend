package problemsService.Model.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SubmissionDTO {

    private String submissionId;
    private String userId;
    private String problemId;
    private String submission;
    private Double averageTime;
    private Double averageMemory;
    private List<RejectCase> rejectedCases;
    private List<AcceptCase> acceptedCases;
    private String sourceCode;
    private LocalDateTime submissionTime;
    private boolean isSolved;
}
