package indiana.indi.indiana.repository.categories;

import indiana.indi.indiana.dtoInterface.categories.CategoryForGameDtoInter;
import indiana.indi.indiana.entity.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Set<CategoryForGameDtoInter> findAllCategoryForNewAndEditGame();

    List<Category> findAllByOrderByTitleAsc();
}
