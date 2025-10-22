package indiana.indi.indiana.repository.manyToMany;

import indiana.indi.indiana.entity.manyToManyEntities.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {
}
