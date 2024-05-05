package problemsService.controller;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problemsService.entities.Constrains;
import problemsService.entities.Example;
import problemsService.entities.Problem;
import problemsService.entities.TestCase;
import problemsService.request.AddProblemRequest;
import problemsService.service.ConstraintService;
import problemsService.service.ExampleService;
import problemsService.service.ProblemService;
import problemsService.service.TestCaseService;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ExampleService exampleService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private ConstraintService constraintService;

    @GetMapping
    public String test(){
        return "hello im ok";
    }


    @PostMapping
    public void addProblem(@RequestBody AddProblemRequest addProblemRequest) {
        Problem problem = new Problem();
        problem.setTitle(addProblemRequest.getTitle());
        problem.setAttemptcount(addProblemRequest.getAttemptcount());
        problem.setSolution(addProblemRequest.getSolution());
        problem.setDescription(addProblemRequest.getDescription());
        problem.setSolvedcount(addProblemRequest.getSolvedcount());
        problem.setDifficulty(addProblemRequest.getDifficulty());

        problemService.saveProblem(problem);

        Example example = new Example();
        example.setProblem(problem);
        example.setExplanation(addProblemRequest.getExplanation());

        exampleService.addExample(example);

        TestCase testCase = new TestCase();
        testCase.setProblem(problem);
        testCase.setInput(addProblemRequest.getInput());
        testCase.setOutput(addProblemRequest.getOutput());

        testCaseService.addTestCase(testCase);

        Constrains constrains = new Constrains();
        constrains.setProblem(problem);
        constrains.setConstraintText(addProblemRequest.getConstraintText());
        constraintService.addConstrains(constrains);

    }



}
