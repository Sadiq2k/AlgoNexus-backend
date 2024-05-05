package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Repository.TestCaseRepository;
import problemsService.entities.TestCase;
import problemsService.service.TestCaseService;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;

    @Override
    public void addTestCase(TestCase testCase) {
        testCaseRepository.save(testCase);
    }
}
