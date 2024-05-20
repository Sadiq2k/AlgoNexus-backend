package com.TestCaseService.FiegnClientCommunication;

import com.TestCaseService.Model.DTO.SubmissionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PROBLEM-SUBMISSION-SERVICE")
public interface SubmissionServiceFeignClient {

    @PostMapping("submission-add")
    ResponseEntity<String> addSubmissionProblem(@RequestBody SubmissionDTO submissionDTO);



}
