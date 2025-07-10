package indiana.indi.indiana.service.categories;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CRUDCategoryServiceImpl implements CRUDCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> findAllCategory(String filter) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(cat -> new CategoryDto(
                        cat.getId(),
                        cat.getTitle(),
                        cat.getGames()
                                .stream()
                                .map(game -> new GameDto(game.getId(), game.getTitle(), game.getImageUrl()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
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
