package indiana.indi.indiana.dtoInterface.games;

import java.math.BigDecimal;

public interface GameInfoProjection {
    Long getId();
    String getTitle();
    String getImageUrl();
    BigDecimal getPrice();
    Long getPurchasesCount();
    double getAverageRating();
    long getRatingsCount();
}
