package problemsService.service;

import problemsService.Model.entities.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategory();
}
