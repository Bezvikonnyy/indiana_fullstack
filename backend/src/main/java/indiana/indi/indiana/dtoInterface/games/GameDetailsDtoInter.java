package indiana.indi.indiana.dtoInterface.games;

import java.math.BigDecimal;

public interface GameDetailsDtoInter {
    Long getId();
    String getTitle();
    String getDetails();
    String getImageUrl();
    String getGameFileUrl();
    Long getAuthorId();
    BigDecimal getPrice();
    Integer getDiscountPercent();
    Integer getRating();
    Boolean getIsFavorite();
    Boolean getIsInCart();
    Boolean getIsPurchased();
}
