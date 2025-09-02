package indiana.indi.indiana.dto.games;

import java.math.BigDecimal;

public record CardItemDto(
        Long id,
        String title,
        String imageUrl,
        BigDecimal price,
        boolean isFavorite,
        boolean isInCart,
        boolean isPurchased) {
}
