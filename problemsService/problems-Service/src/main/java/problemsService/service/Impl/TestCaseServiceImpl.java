//package problemsService.service.Impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import problemsService.Repository.TestCaseRepository;
//import problemsService.Model.Dto.TestCase;
//import problemsService.service.TestCaseService;
//
//import java.util.List;
//
//@Service
//public class TestCaseServiceImpl implements TestCaseService {
//    @Autowired
//    private TestCaseRepository testCaseRepository;
//
//    @Override
//    public void addTestCase(TestCase testCase) {
//        testCaseRepository.save(testCase);
//    }
//
//    @Override
//    public void saveAll(List<TestCase> testCases) {
//        testCaseRepository.saveAll(testCases);
//    }
//
//
//}
//
