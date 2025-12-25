package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.categories.CategoryForGameDto;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.repository.categories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> validCategoryByGame(List<Long> categoryId) {
        List<Category> categories = categoryRepository.findAllById(categoryId);

        if(categories.size() != categoryId.size()){
            throw  new EntityNotFoundException("Category not found.");
        }
        return categories;
    }

    @Override
    public Iterable<Category> findAllCategory(String filter) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Set<CategoryForGameDto> findCategoryForGame(){
        return categoryRepository.findAllCategoryForNewAndEditGame().stream()
                .map(cat -> new CategoryForGameDto(cat.getId(), cat.getTitle())).collect(Collectors.toSet());
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

}
