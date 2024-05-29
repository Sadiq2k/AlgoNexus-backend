package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Model.entities.Difficulties;
import problemsService.Repository.DifficultyRepository;
import problemsService.service.DifficultyService;

import java.util.List;
import java.util.Optional;

@Service
public class DifficultyServiceImpl implements DifficultyService {

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Override
    public void addDifficulty(Difficulties difficulties) {
        difficultyRepository.save(difficulties);
    }

    @Override
    public List<Difficulties> getAllDifficulty() {
        return difficultyRepository.findAll();
    }

    @Override
    public void getDifficulty(String id) {
        Optional<Difficulties> optionalDifficulties = difficultyRepository.findById(id);

        if (optionalDifficulties.isPresent()) {
            Difficulties difficulties = optionalDifficulties.get();
            difficulties.setTotalCount(difficulties.getTotalCount() + 1);
            difficultyRepository.save(difficulties);
        }
    }
}

