package indiana.indi.indiana.mapperInterface.games;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import org.springframework.stereotype.Component;

@Component
public class CardItemMapper {

    public CardItemDto toDto(CardItemDtoInter dtoInter) {
        return new CardItemDto(
                dtoInter.getId(),
                dtoInter.getTitle(),
                dtoInter.getImageUrl(),
                dtoInter.getPrice(),
                dtoInter.getIsFavorite(),
                dtoInter.getIsInCart(),
                dtoInter.getIsPurchased()
        );
    }
}
