package indiana.indi.indiana.mapper.games;

import indiana.indi.indiana.dto.categories.CategoryForGameDto;
import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.dtoInterface.categories.CategoryForGameDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameDetailsDtoInter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameDetailsMapper {

    public GameDetailsDto toDto(GameDetailsDtoInter game, Set<CategoryForGameDtoInter> categories) {
        return new GameDetailsDto(
                game.getId(),
                game.getTitle(),
                game.getDetails(),
                game.getImageUrl(),
                game.getGameFileUrl(),
                game.getAuthorId(),
                categories.stream().map(category -> new CategoryForGameDto(
                        category.getId(),
                        category.getTitle())).collect(Collectors.toList()),
                game.getPrice(),
                game.getIsFavorite(),
                game.getIsInCart(),
                game.getIsPurchased()
        );
    }
}
