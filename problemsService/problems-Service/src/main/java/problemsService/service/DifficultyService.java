package problemsService.service;

import problemsService.Model.entities.Difficulties;

import java.util.List;

public interface DifficultyService {
    void addDifficulty(Difficulties difficulties);

    List<Difficulties> getAllDifficulty();

    void getDifficulty(String id);
}
