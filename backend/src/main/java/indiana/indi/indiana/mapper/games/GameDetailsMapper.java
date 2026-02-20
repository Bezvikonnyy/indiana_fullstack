package indiana.indi.indiana.mapper.games;

import indiana.indi.indiana.dto.categories.CategoryDto;
import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.dtoInterface.categories.CategoryDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameDetailsDtoInter;
import indiana.indi.indiana.service.game.GameService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameDetailsMapper {

    private final GameService service;

    public GameDetailsMapper(GameService service) {
        this.service = service;
    }

    public GameDetailsDto toDto(GameDetailsDtoInter game, Set<CategoryDtoInter> categories) {
        BigDecimal finalPrice = service.finalPrice(game.getPrice(), game.getDiscountPercent());
        return new GameDetailsDto(
                game.getId(),
                game.getTitle(),
                game.getDetails(),
                game.getImageUrl(),
                game.getGameFileUrl(),
                game.getAuthorId(),
                categories.stream().map(category -> new CategoryDto(
                        category.getId(),
                        category.getTitle())).collect(Collectors.toList()),
                game.getPrice(),
                game.getDiscountPercent() != null ? game.getDiscountPercent() : 0,
                finalPrice,
                game.getRating(),
                game.getIsFavorite(),
                game.getIsInCart(),
                game.getIsPurchased()
        );
    }
}
