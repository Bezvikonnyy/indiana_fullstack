package indiana.indi.indiana.dtoInterface.games;

import java.math.BigDecimal;

public interface GameWithCategoryDtoInter {
    Long getId();
    String getTitle();
    String getImageUrl();
    BigDecimal getPrice();
    Boolean getIsFavorite();
    Boolean getIsInCart();
    Boolean getIsPurchased();
    Long getCategoryId();
}
