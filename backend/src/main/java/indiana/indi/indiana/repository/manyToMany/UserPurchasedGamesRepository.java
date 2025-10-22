package indiana.indi.indiana.repository.manyToMany;

import indiana.indi.indiana.entity.manyToManyEntities.UserPurchasedGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPurchasedGamesRepository extends JpaRepository<UserPurchasedGames, Long> {
}
