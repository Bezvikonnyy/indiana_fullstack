package indiana.indi.indiana.repository.categories;

import indiana.indi.indiana.dtoInterface.categories.CategoryDtoInter;
import indiana.indi.indiana.entity.categories.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT 
                c.id as id,
                c.title as title
            FROM Category c
            """)
    Set<CategoryDtoInter> findAllCategoryForNewAndEditGame();

    @Query("""
            SELECT
                c.id as id,
                c.title as title
            FROM Category c
            """)
    Page<CategoryDtoInter> findCategoriesForPage(Pageable pageable);

    @Query("""
                SELECT c.id as id, c.title as title
                FROM GameCategory gc
                JOIN gc.category c
                WHERE gc.game.id = :gameId
            """)
    Set<CategoryDtoInter> getCategoriesForGameDetails(@Param("gameId") Long gameId);
}
