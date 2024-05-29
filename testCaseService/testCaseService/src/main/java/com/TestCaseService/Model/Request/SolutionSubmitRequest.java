package com.TestCaseService.Model.Request;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Validated
public class SolutionSubmitRequest {

    private String problemId;
    private Long problemNo;
    private String title;
    private String userId;
    private String difficulty;
    private Integer languageId;
    private String language;
    private String solutionCode;
    private String driverCode;
}
