package indiana.indi.indiana.dtoInterface;

import java.util.List;

public interface CategoryDtoInter {
    Long getId();
    String getTitle();
    List<CardItemDtoInter> getGames();
}
