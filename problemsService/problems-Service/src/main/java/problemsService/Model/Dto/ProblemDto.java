package problemsService.Model.Dto;

import lombok.*;
import problemsService.Model.entities.Problem;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDto {

    private String problemId;
    private Long problemNo;
    private String title;
    private String description;
    private String difficulty;
    private String driverCode;
    private Integer languageId;
    private List<String> constraints;
    private String solutionTemplate;
    private Float acceptanceRate;
    private LocalDateTime createdAt;
    private String category;
    private List<Example> examples;
    private List<TestCase> testCases;

    public ProblemDto(Problem problem){
        this.problemId = problem.getProblemId();
        this.problemNo = problem.getProblemNo();
        this.title = problem.getTitle();
        this.description = problem.getDescription();
        this.difficulty = problem.getDifficulty();
        this.driverCode = problem.getDriverCode();
        this.constraints = problem.getConstraints();
        this.solutionTemplate = problem.getSolutionTemplate();
        this.acceptanceRate = problem.getAcceptanceRate();
        this.category = problem.getCategory();
        this.examples = problem.getExamples();
        this.testCases = problem.getTestCases();
        this.languageId = problem.getLanguageId();
        this.createdAt =problem.getCreatedAt();
    }
}
