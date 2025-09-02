package indiana.indi.indiana.mapperInterface.games;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapperInterface {

    private final CartRepository cartRepository;

    public CardItemDto toDto(CardItemDtoInter projection) {

        return new CardItemDto(
                projection.getId(),
                projection.getTitle(),
                projection.getImageUrl(),
                projection.getPrice(),
                projection.getIsFavorite(),
                projection.getIsInCart(),
                projection.getIsPurchased());
    }
}
