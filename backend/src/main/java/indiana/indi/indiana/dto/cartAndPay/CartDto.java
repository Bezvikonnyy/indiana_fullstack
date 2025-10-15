package indiana.indi.indiana.dto.cartAndPay;

import java.math.BigDecimal;
import java.util.Set;

public record CartDto(Long id, Long userId, Set<CartItemDto> items, int totalItems, BigDecimal totalPrice) {
}
