package indiana.indi.indiana.dtoInterface;

import java.math.BigDecimal;
import java.util.List;

public interface CartDtoInter {
    Long getId();
    Long getUserId();
    List<CartItemDtoInter> getItems();
    int getTotalItems();
    BigDecimal getTotalPrice();
}
