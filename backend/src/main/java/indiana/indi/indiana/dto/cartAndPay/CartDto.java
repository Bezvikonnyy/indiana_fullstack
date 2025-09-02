package indiana.indi.indiana.dto.cartAndPay;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(Long id, Long userId, List<CartItemDto> items, int totalItems, BigDecimal totalPrice) {
}
