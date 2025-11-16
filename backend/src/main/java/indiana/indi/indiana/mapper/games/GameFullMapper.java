package indiana.indi.indiana.mapper.games;

import indiana.indi.indiana.dto.categories.CategoryForGameDto;
import indiana.indi.indiana.dto.games.GameFullDto;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.games.Game;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameFullMapper {

    public GameFullDto toDto(Game game, List<Category> categories) {
        return new GameFullDto(
                game.getId(),
                game.getTitle(),
                game.getDetails(),
                game.getImageUrl(),
                game.getGameFileUrl(),
                game.getAuthor().getId(),
                categories.stream().map(category -> new CategoryForGameDto(
                        category.getId(),
                        category.getTitle())).collect(Collectors.toList()),
                game.getPrice()
                );
    }
}
