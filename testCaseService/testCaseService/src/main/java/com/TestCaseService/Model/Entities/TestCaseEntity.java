package com.TestCaseService.Model.Entities;

import com.TestCaseService.Model.TestCases.TestCase;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseEntity {

    @Id
    private String test_id;
    private List<TestCase> testCases;
    private String problemId;
}
