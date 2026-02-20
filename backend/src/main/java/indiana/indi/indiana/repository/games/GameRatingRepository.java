package indiana.indi.indiana.repository.games;

import indiana.indi.indiana.entity.manyToManyEntities.GameRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRatingRepository extends JpaRepository<GameRating, Long> {

    Optional<GameRating> findByUserIdAndGameId(Long userId, Long gameId);
}

