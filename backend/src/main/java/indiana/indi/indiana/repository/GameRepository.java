package indiana.indi.indiana.repository;

import indiana.indi.indiana.dtoInterface.CartItemDto;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByTitleLikeIgnoreCase(String filter);

    List<Game> findByCategoriesContaining(Category category);

    @Query("""
        SELECT 
            g.id as id,
            g.title as title,
            g.imageUrl as imageUrl,
            g.price as price,
            CASE WHEN :user MEMBER OF g.favorites THEN true ELSE false END as isFavorite,
            CASE WHEN :user IN (SELECT i.cart.user FROM CartItem i WHERE i.game = g) THEN true ELSE false END as isInCart,
            CASE WHEN :user MEMBER OF g.buyers THEN true ELSE false END as isPurchased
        FROM Game g
            JOIN g.categories c
            WHERE c.id = :categoryId
            """)
    List<CartItemDto> findAllByCategoryWithUserStatus(
            @Param("categoryId") Long categoryId,
            @Param("user") indiana.indi.indiana.entity.User user
    );

}
