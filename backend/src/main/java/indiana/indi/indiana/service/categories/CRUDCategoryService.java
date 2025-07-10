package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.entity.Category;

import java.util.List;

public interface CRUDCategoryService {

    Iterable<Category> findAllCategory(String filter);

    List<CategoryDto> findAll();

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

}
