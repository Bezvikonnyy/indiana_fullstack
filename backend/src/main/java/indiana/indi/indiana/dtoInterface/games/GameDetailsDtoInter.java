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
    Boolean getIsFavorite();
    Boolean getIsInCart();
    Boolean getIsPurchased();
}
