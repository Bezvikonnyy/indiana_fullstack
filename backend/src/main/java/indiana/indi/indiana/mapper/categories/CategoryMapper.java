package indiana.indi.indiana.mapper.categories;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.mapper.games.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final GameMapper gameMapper;
    public CategoryDto toDto(Category category, User user) {
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                category.getGames().stream()
                        .map(g -> gameMapper.toDto(g, user))
                        .collect(Collectors.toList())
        );
    }
}
