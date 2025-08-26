package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDto toDto(Game game) {
        return new GameDto(game.getId(), game.getTitle(), game.getImageUrl(), game.getPrice());
    }
}
