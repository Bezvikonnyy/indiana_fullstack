package indiana.indi.indiana.dto.games;

import java.math.BigDecimal;

public record GameInfoDto(
        Long id,
        String title,
        String imageUrl,
        BigDecimal price,
        BigDecimal finalPrice,
        Long purchasesCount,
        double averageRating,
        Long ratingsCount
) {}
