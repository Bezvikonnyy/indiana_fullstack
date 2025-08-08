package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CategoryForGameDto;
import indiana.indi.indiana.dto.GameFullDto;
import indiana.indi.indiana.entity.Game;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameFullMapper {

    public GameFullDto toDto(Game game) {
        return new GameFullDto(
                game.getId(),
                game.getTitle(),
                game.getDetails(),
                game.getImageUrl(),
                game.getGameFileUrl(),
                game.getAuthor().getId(),
                game.getCategories().stream().map(category -> new CategoryForGameDto(
                        category.getId(),
                        category.getTitle())).collect(Collectors.toList()),
                game.getPrice()
                );
    }
}
