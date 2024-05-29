package problemsService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problemsService.Model.entities.Difficulties;
import problemsService.service.DifficultyService;

import java.util.List;

@RestController
@RequestMapping("/difficulty")
public class DifficultyController {

    @Autowired
    private DifficultyService difficultyService;

    @PostMapping("/add")
    public void addDifficulty(@RequestBody Difficulties difficulties){
        difficultyService.addDifficulty(difficulties);
    }
    @GetMapping("/get")
    private List<Difficulties> getAllDifficulty(){
       return difficultyService.getAllDifficulty();
    }

    @PostMapping("/add/{id}")
    public void updateTotalCountOfDifficulty(@PathVariable("id") String id){
        difficultyService.getDifficulty(id);
    }

}
