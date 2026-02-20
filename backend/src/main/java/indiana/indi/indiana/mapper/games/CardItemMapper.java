package indiana.indi.indiana.mapper.games;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.service.game.GameService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardItemMapper {

    private final GameService service;

    public CardItemMapper(GameService service) {
        this.service = service;
    }

    public CardItemDto toDto(CardItemDtoInter dtoInter) {
        BigDecimal finalPrice = service.finalPrice(dtoInter.getPrice(), dtoInter.getDiscountPercent());
        return new CardItemDto(
                dtoInter.getId(),
                dtoInter.getTitle(),
                dtoInter.getImageUrl(),
                dtoInter.getPrice(),
                dtoInter.getDiscountPercent() != null ? dtoInter.getDiscountPercent() : 0,
                finalPrice,
                dtoInter.getRating(),
                dtoInter.getCategoryId(),
                dtoInter.getIsFavorite(),
                dtoInter.getIsInCart(),
                dtoInter.getIsPurchased()
        );
    }
}

