package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Repository.ProblemRepository;
import problemsService.entities.Problem;
import problemsService.service.ProblemService;

@Service
public class ProblemServiceImp implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public void saveProblem(Problem problem) {
        problemRepository.save(problem);
    }
}
