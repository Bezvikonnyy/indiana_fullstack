package indiana.indi.indiana.service;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapperInterface.GameMapperInterface;
import indiana.indi.indiana.repository.CategoryRepository;
import indiana.indi.indiana.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultHomeService implements HomeService {

    private final CategoryRepository categoryRepository;

    private final GameMapperInterface gameMapper;

    private final GameRepository gameRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public List<CategoryDto> getCategoriesGamesDto(User user) {
        return getAllCategories().stream().map(c -> new CategoryDto(
                c.getId(),
                c.getTitle(),
                gameRepository.findAllByCategoryWithUserStatus(c.getId(), user)
                        .stream().map(g -> gameMapper.toDto(g)).toList()
        )).toList();
    }
}
