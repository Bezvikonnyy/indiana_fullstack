package indiana.indi.indiana.mapper.categories;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.dtoInterface.categories.CategoryDtoInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryDto toDto(CategoryDtoInter inter) {
        return new CategoryDto(
                inter.getId(),
                inter.getTitle()
        );
    }
}
