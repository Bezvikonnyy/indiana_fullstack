package indiana.indi.indiana.dtoInterface;

import java.math.BigDecimal;

public interface CardItemDtoInter {
    Long getId();
    String getTitle();
    String getImageUrl();
    BigDecimal getPrice();

    Boolean getIsFavorite();
    Boolean getIsInCart();
    Boolean getIsPurchased();
}
