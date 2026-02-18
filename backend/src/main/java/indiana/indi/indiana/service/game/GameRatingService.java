package indiana.indi.indiana.service.game;

import indiana.indi.indiana.entity.manyToManyEntities.GameRating;
import indiana.indi.indiana.repository.games.GameRatingRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.manyToMany.UserPurchasedGamesRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRatingService {

    private final GameRatingRepository ratingRepository;
    private final UserPurchasedGamesRepository purchasedGamesRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Transactional
    public void rateGame(Long userId, Long gameId, int ratingValue) {
        // Проверяем, что пользователь купил игру
        boolean purchased = purchasedGamesRepository.existsByUserIdAndGameId(userId, gameId);
        if (!purchased) {
            throw new RuntimeException("User must purchase the game before rating");
        }

        GameRating rating = ratingRepository.findByUserIdAndGameId(userId, gameId)
                .orElse(new GameRating());

        rating.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        rating.setGame(gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found")));
        rating.setRating(ratingValue);

        ratingRepository.save(rating);
    }
}
