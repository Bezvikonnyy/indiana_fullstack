package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Iterable<Game> findAllByTitleLikeIgnoreCase(String filter);

    List<Game> findByCategoriesContaining(Category category);

}
