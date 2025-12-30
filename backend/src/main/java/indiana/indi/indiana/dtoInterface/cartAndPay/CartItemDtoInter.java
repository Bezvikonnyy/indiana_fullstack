package indiana.indi.indiana.dtoInterface.cartAndPay;

import java.math.BigDecimal;

public interface CartItemDtoInter {
    Long getId();
    Long getGameId();
    String getGameTitle();
    BigDecimal getPrice();

    String getImageUrl();
}
