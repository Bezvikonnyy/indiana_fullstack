package indiana.indi.indiana.dtoInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDtoInter {
    Long getId();
    Long getUserId();
    List<OrderItemDtoInter> getItems();
    BigDecimal getTotalPrice();
    String getStatus();
    LocalDateTime getCreatedAt();
}
