package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.User;
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
