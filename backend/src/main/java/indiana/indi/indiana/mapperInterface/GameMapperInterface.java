package indiana.indi.indiana.mapperInterface;

import indiana.indi.indiana.dto.CardItemDto;
import indiana.indi.indiana.dtoInterface.CardItemDtoInter;
import indiana.indi.indiana.repository.CartRepository;
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
