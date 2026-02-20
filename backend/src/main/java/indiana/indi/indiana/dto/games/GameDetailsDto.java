package indiana.indi.indiana.dto.games;

import indiana.indi.indiana.dto.categories.CategoryDto;

import java.math.BigDecimal;
import java.util.List;

public record GameDetailsDto(
        Long id,
        String title,
        String details,
        String imageUrl,
        String gameFileUrl,
        Long authorId,
        List<CategoryDto> categories,
        BigDecimal price,
        Integer discountPercent,
        BigDecimal finalPrice,
        Integer rating,
        boolean isFavorite,
        boolean isInCart,
        boolean isPurchased
) {
}
