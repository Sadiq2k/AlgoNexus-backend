package com.TestCaseService.Repository;

import com.TestCaseService.Model.Entities.TestCaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCaseEntity,String> {

    List<TestCaseEntity> findByProblemId(String problemId);
}
