package indiana.indi.indiana.service.home;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.users.User;

import java.util.List;

public interface HomeService {

    List<Category> getAllCategories();

    List<CategoryDto> getCategoriesGamesDto(Long userId);

}
