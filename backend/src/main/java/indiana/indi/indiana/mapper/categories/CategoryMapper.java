package indiana.indi.indiana.mapper.categories;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.mapperInterface.games.GameMapperInterface;
import indiana.indi.indiana.repository.games.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final GameMapperInterface gameMapper;
    private final GameRepository gameRepository;

    public CategoryDto toDto(Category category, Long userId) {
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                gameRepository.findAllByCategoryWithUserStatus(category.getId(), userId)
                        .stream().map(gameMapper::toDto).toList()
        );
    }
}
