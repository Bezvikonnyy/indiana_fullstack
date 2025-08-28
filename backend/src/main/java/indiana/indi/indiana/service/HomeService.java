package indiana.indi.indiana.service;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.User;

import java.util.List;

public interface HomeService {

    List<Category> getAllCategories();

    List<CategoryDto> getCategoriesGamesDto(User user);

}
