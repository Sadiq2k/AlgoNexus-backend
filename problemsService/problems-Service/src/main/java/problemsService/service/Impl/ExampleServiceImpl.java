package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Model.Dto.Example;
import problemsService.Repository.ExampleRepository;
import problemsService.service.ExampleService;

@Service
public class ExampleServiceImpl implements ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;
    @Override
    public void addExample(Example example) {
        exampleRepository.save(example);
    }
}
