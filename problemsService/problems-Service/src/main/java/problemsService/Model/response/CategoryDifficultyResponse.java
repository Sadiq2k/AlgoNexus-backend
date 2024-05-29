package problemsService.Model.response;

import lombok.Getter;
import lombok.Setter;
import problemsService.Model.entities.Category;
import problemsService.Model.entities.Difficulties;

import java.util.List;

@Getter
@Setter
public class CategoryDifficultyResponse {
    private List<Category> categories;
    private List<Difficulties> difficulties;


}
