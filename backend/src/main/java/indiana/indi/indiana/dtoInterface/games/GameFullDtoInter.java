package indiana.indi.indiana.dtoInterface.games;

import indiana.indi.indiana.dtoInterface.categories.CategoryForGameDtoInter;

import java.math.BigDecimal;
import java.util.List;

public interface GameFullDtoInter {
    Long getId();
    String getTitle();
    String getDetails();
    String getImageUrl();
    String getGameFileUrl();
    Long getAuthorId();
    List<CategoryForGameDtoInter> getCategories();
    BigDecimal getPrice();
}
