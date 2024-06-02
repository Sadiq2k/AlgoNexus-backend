package problemsService.Model.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import problemsService.Model.Dto.Example;
import problemsService.Model.Dto.SubmissionDTO;
import problemsService.Model.Dto.TestCase;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
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
    private List<SubmissionDTO> submissionDTO;


}