package indiana.indi.indiana.dtoInterface.categories;

import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;

import java.util.List;

public interface CategoryDtoInter {
    Long getId();
    String getTitle();
    List<CardItemDtoInter> getGames();
}
