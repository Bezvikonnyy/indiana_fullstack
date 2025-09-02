package indiana.indi.indiana.dto.cartAndPay;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderItemDto> items,
        BigDecimal totalPrice,
        String status,
        LocalDateTime createdAt) {
}
