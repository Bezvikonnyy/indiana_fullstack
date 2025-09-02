package indiana.indi.indiana.dtoInterface;

import java.math.BigDecimal;

public interface OrderItemDtoInter {
    Long getId();
    Long getGameId();
    String getGameTitle();
    BigDecimal getPrice();
    int getQuantity();
}
