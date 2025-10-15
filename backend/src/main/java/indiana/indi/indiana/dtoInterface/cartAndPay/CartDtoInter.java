package indiana.indi.indiana.dtoInterface.cartAndPay;

import java.math.BigDecimal;
import java.util.List;

public interface CartDtoInter {
    Long getId();
    Long getUserId();
    int getTotalItems();
    BigDecimal getTotalPrice();
}
