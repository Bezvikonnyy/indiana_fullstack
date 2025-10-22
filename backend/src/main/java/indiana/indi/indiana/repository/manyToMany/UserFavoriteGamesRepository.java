package indiana.indi.indiana.repository.manyToMany;

import indiana.indi.indiana.entity.manyToManyEntities.UserFavoriteGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteGamesRepository extends JpaRepository<UserFavoriteGames, Long> {
}
