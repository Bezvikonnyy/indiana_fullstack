package indiana.indi.indiana.mapperInterface.games;

import indiana.indi.indiana.dto.games.GameForProfileDto;
import indiana.indi.indiana.dtoInterface.games.GameForProfileDtoInter;
import org.springframework.stereotype.Component;

@Component
public class GameForProfileMapperInterface {

    public GameForProfileDto toDto(GameForProfileDtoInter dto) {
        return new GameForProfileDto(dto.getId(), dto.getTitle());
    }
}
