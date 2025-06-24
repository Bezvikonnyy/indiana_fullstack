package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CRUDCategoryService {

    Iterable<Category> findAllCategory(String filter);

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

}
