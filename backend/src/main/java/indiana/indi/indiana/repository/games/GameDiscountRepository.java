package indiana.indi.indiana.repository.games;

import indiana.indi.indiana.entity.games.GameDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GameDiscountRepository extends JpaRepository<GameDiscount, Long> {
    @Query("""
            SELECT d
            FROM GameDiscount d 
            WHERE d.game.id = :gameId AND d.active = true  
            AND :now BETWEEN d.startDate AND d.endDate""")
    GameDiscount findActiveDiscountByGame(@Param("gameId") Long gameId, @Param("now") LocalDateTime now);
}
