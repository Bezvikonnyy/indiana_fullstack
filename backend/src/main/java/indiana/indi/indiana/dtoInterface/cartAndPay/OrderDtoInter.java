package indiana.indi.indiana.dtoInterface.cartAndPay;

import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDtoInter {
    Long getId();
    Long getUserId();
    BigDecimal getTotalPrice();
    String getStatus();
    LocalDateTime getCreatedAt();
}
