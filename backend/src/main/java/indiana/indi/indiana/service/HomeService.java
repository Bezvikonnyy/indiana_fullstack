package indiana.indi.indiana.service;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;

import java.util.List;
import java.util.Map;

public interface HomeService {

    List<Category> getAllCategories();

    List<CategoryDto> getCategoriesGamesDto();

}
