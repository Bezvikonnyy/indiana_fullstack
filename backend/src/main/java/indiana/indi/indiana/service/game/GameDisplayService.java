package indiana.indi.indiana.service.game;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.games.GameInfoDto;
import indiana.indi.indiana.dtoInterface.games.GameInfoProjection;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.entity.games.GameDiscount;
import indiana.indi.indiana.repository.games.GameDiscountRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameDisplayService {

    private final GameRepository gameRepository;
    private final GameDiscountRepository discountRepository;

    // 1. Все игры с рейтингами
    @Transactional
    public List<GameInfoDto> getAllGames() {
        List<GameInfoProjection> projections = gameRepository.findAllWithRatings();
        return projections.stream()
                .map(this::mapToGameInfoDto)
                .collect(Collectors.toList());
    }

    private GameInfoDto mapToGameInfoDto(GameInfoProjection p) {
        BigDecimal finalPrice = calculateFinalPrice(p.getId(), p.getPrice());
        return new GameInfoDto(
                p.getId(),
                p.getTitle(),
                p.getImageUrl(),
                p.getPrice(),
                finalPrice,
                p.getPurchasesCount(),
                p.getAverageRating(),
                p.getRatingsCount()
        );
    }

    // 2. Новинки
    @Transactional
    public Page<CardItemDto> getLatestGames(Long userId, Pageable pageable) {
        return gameRepository.findLatestGames(userId, pageable)
                .map(this::mapToCardItemDto);
    }

    // 3. Популярные
    @Transactional
    public Page<CardItemDto> getPopularGames(Long userId, Pageable pageable) {
        return gameRepository.findPopularGames(userId, pageable)
                .map(this::mapToCardItemDto);
    }

    // 4. Игры со скидкой
    @Transactional
    public Page<CardItemDto> getDiscountedGames(Long userId, Pageable pageable) {
        return gameRepository.findDiscountedGames(userId, LocalDateTime.now(), pageable)
                .map(this::mapToCardItemDto);
    }

    // --- Вспомогательные методы ---

    private CardItemDto mapToCardItemDto(CardItemDtoInter p) {
        BigDecimal finalPrice = calculateFinalPrice(p.getId(), p.getPrice());
        return new CardItemDto(
                p.getId(),
                p.getTitle(),
                p.getImageUrl(),
                p.getPrice(),
                finalPrice,
                p.getDiscountPercent(),
                p.getIsFavorite(),
                p.getIsInCart(),
                p.getIsPurchased()
        );
    }

    private BigDecimal calculateFinalPrice(Long gameId, BigDecimal price) {
        GameDiscount discount = discountRepository.findActiveDiscountByGame(gameId, LocalDateTime.now());
        if (discount != null) {
            return price.subtract(
                    price.multiply(BigDecimal.valueOf(discount.getDiscountPercent()))
                            .divide(BigDecimal.valueOf(100))
            );
        }
        return price;
    }
}
