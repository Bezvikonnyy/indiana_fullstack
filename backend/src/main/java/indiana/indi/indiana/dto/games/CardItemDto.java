package indiana.indi.indiana.dto.games;

import java.math.BigDecimal;

public record CardItemDto(
        Long id,
        String title,
        String imageUrl,
        BigDecimal price,
        Integer discountPercent,
        BigDecimal finalPrice,
        Integer rating,
        Long categoryId,
        boolean isFavorite,
        boolean isInCart,
        boolean isPurchased) {
}
