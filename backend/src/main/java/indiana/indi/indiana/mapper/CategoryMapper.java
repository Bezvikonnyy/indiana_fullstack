package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CategoryDto;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.entity.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                category.getGames().stream()
                        .map(g -> new GameDto(g.getId(), g.getTitle(), g.getImageUrl())).collect(Collectors.toList())
        );
    }
}
