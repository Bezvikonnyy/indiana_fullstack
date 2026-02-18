package indiana.indi.indiana.service.game;

import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.games.GameDiscount;
import indiana.indi.indiana.repository.games.GameDiscountRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameDiscountService {

    private final GameDiscountRepository discountRepository;
    private final GameRepository gameRepository;

    @Transactional
    public GameDiscount createDiscount(Long gameId, int discountPercent, LocalDateTime startDate, LocalDateTime endDate) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        GameDiscount discount = new GameDiscount();
        discount.setGame(game);
        discount.setDiscountPercent(discountPercent);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setActive(true);

        return discountRepository.save(discount);
    }

    @Transactional
    public GameDiscount updateDiscount(Long discountId, int discountPercent, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        GameDiscount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found"));

        discount.setDiscountPercent(discountPercent);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setActive(active);

        return discountRepository.save(discount);
    }

    public GameDiscount getActiveDiscount(Long gameId) {
        return discountRepository.findActiveDiscountByGame(gameId, LocalDateTime.now());
    }

    public List<GameDiscount> getAllDiscountsForGame(Long gameId) {
        return discountRepository.findAll().stream()
                .filter(d -> d.getGame().getId().equals(gameId))
                .toList();
    }
}
