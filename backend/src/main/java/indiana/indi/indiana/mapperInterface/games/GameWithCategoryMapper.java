package indiana.indi.indiana.mapperInterface.games;

import indiana.indi.indiana.dto.games.GameWithCategoryDto;
import indiana.indi.indiana.dtoInterface.games.GameWithCategoryDtoInter;
import org.springframework.stereotype.Component;

@Component
public class GameWithCategoryMapper {

    public GameWithCategoryDto toDto(GameWithCategoryDtoInter dtoInter) {
        return new GameWithCategoryDto(
                dtoInter.getId(),
                dtoInter.getTitle(),
                dtoInter.getImageUrl(),
                dtoInter.getPrice(),
                dtoInter.getIsFavorite(),
                dtoInter.getIsInCart(),
                dtoInter.getIsPurchased(),
                dtoInter.getCategoryId()
        );
    }
}
