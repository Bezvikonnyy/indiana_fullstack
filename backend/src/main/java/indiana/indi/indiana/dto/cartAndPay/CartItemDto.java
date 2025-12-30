package indiana.indi.indiana.dto.cartAndPay;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        Long gameId,
        String gameTitle,
        BigDecimal price,
        String imageUrl) {
}
