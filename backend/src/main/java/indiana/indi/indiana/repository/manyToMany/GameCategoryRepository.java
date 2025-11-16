package indiana.indi.indiana.repository.manyToMany;

import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.manyToManyEntities.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {

    @Query("""
            SELECT
                gc.category
            FROM GameCategory gc
            WHERE gc.game.id =:gameId
            """)
    List<Category> findByGameId(@Param("gameId") Long gameId);
}
