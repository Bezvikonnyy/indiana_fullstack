package indiana.indi.indiana.dtoInterface;

import indiana.indi.indiana.dto.CategoryForGameDto;

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
