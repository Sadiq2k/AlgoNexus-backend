package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Repository.ExampleRepository;
import problemsService.entities.Example;
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
