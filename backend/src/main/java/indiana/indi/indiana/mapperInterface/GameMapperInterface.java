package indiana.indi.indiana.mapperInterface;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dtoInterface.CartItemDto;
import indiana.indi.indiana.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapperInterface {

    private final CartRepository cartRepository;

    public GameDto toDto(CartItemDto projection) {

        return new GameDto(
                projection.getId(),
                projection.getTitle(),
                projection.getImageUrl(),
                projection.getPrice(),
                projection.getIsFavorite(),
                projection.getIsInCart(),
                projection.getIsPurchased());
    }
}
