package indiana.indi.indiana.service;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultHomeService implements HomeService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDto> getCategoriesGamesDto() {
        return getAllCategories().stream().map(c -> new CategoryDto(
                c.getId(),
                c.getTitle(),
                c.getGames().stream().map(g -> new GameDto(g.getId(), g.getTitle(), g.getImageUrl(),g.getPrice()))
                        .toList()
        )).toList();
    }

}
