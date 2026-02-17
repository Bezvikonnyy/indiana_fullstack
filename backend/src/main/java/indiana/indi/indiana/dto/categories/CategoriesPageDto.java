package indiana.indi.indiana.dto.categories;

import java.util.List;

public record CategoriesPageDto(
        List<CategoryWithGamesDto> categories,
        Integer page,
        Integer totalPages,
        Long totalElements
) {
}
