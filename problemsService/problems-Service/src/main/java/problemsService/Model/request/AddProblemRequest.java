package problemsService.Model.request;

import lombok.Getter;
import lombok.Setter;
import problemsService.Model.Dto.Example;
import problemsService.Model.Dto.TestCase;

import java.util.List;

@Getter
@Setter
public class AddProblemRequest {

//    private String title;
//    private String description;
//    private String difficulty;
//    private String driverCode;
//    private Integer languageId;
//    private List<String> constraints;
//    private String solutionTemplate;
//    private Float acceptanceRate;
//    private String category;
//    private List<Example> examples;
//    private List<TestCase> testCases;
    private String title;
    private String description;
    private String difficulty;
    private String driverCode;
    private Integer languageId;
    private List<String> constraints;
    private String solutionTemplate;
//    private Float acceptanceRate;
    private String category;
    private List<Example> examples;
    private List<TestCase> testCases;
}

