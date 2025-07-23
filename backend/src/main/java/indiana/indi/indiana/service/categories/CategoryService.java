package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.entity.Category;

import java.util.List;

public interface CategoryService {

    Iterable<Category> findAllCategory(String filter);

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

}
