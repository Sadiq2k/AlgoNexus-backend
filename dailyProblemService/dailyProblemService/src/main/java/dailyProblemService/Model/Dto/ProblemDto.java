package dailyProblemService.Model.Dto;

import dailyProblemService.Model.Example;
import dailyProblemService.Model.TestCase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProblemDto {


    private String problemId;
    @Field("problemNo")
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

}
