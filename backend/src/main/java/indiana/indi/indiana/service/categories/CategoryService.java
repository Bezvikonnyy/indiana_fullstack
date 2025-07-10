package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> validCategoryByGame(List<Long> categoryId) {

        List<Category> categories = categoryRepository.findAllById(categoryId);

        if(categories.size() != categoryId.size()){
            throw  new EntityNotFoundException("Category not found.");
        }
        return categories;
    }

}
