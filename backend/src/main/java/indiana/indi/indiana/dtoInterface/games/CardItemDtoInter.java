package indiana.indi.indiana.dtoInterface.games;

import java.math.BigDecimal;

public interface CardItemDtoInter {
    Long getId();
    String getTitle();
    String getImageUrl();
    BigDecimal getPrice();
    Integer getDiscountPercent();
    Integer getRating();
    Long getCategoryId();
    Boolean getIsFavorite();
    Boolean getIsInCart();
    Boolean getIsPurchased();
}
