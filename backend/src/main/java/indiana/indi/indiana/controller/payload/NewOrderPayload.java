package indiana.indi.indiana.controller.payload;

import java.math.BigDecimal;

public record NewOrderPayload(Long userId, BigDecimal totalAmount) {
}
