package problemsService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import problemsService.Model.entities.Category;
import problemsService.Model.entities.Difficulties;
import problemsService.Model.response.CategoryDifficultyResponse;
import problemsService.service.CategoryService;
import problemsService.service.DifficultyService;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DifficultyService difficultyService;

    @PostMapping("/add")
    public void addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
    }
    @GetMapping("/get")
    public CategoryDifficultyResponse getAllCategoryAndDifficulty(@RequestParam(value = "category",required = false) boolean category) {
        List<Category> allCategories = categoryService.getAllCategory();
        List<Difficulties> allDifficulties = difficultyService.getAllDifficulty();
        CategoryDifficultyResponse response = new CategoryDifficultyResponse();
        response.setCategories(allCategories);

        if(category){
            return response;
        }

        response.setDifficulties(allDifficulties);

        return response;
    }

}
