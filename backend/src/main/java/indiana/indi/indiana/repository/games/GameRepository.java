package indiana.indi.indiana.repository.games;

import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameForProfileDtoInter;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByTitleLikeIgnoreCase(String filter);

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
    List<CardItemDtoInter> findAllByCategoryWithUserStatus(
            @Param("categoryId") Long categoryId,
            @Param("user") User user
    );

    @Query("""
            SELECT 
                g.id as id,
                g.title as title
            FROM Game g
            WHERE g.author.id =:userId
            """)
    List<GameForProfileDtoInter> findAuthorsGameById(@Param("userId") Long userId);

    @Query("""
            SELECT
                g.id as id,
                g.title as title 
            FROM Game g
            JOIN g.buyers u
            WHERE u.id=:userId
            """)
    Set<GameForProfileDtoInter> findBuyersGameById(@Param("userId") Long userId);

    @Query("""
            SELECT 
                g.id as id,
                g.title as title
            FROM Game g
            JOIN g.favorites f
            WHERE f.id=:userId
            """)
    Set<GameForProfileDtoInter> findFavoritesGameById(@Param("userId") Long userId);

    @Query("""
                SELECT 
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.favorites f WHERE f.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.buyers b WHERE b.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM Game g
                WHERE g.author.id = :userId
            """)
    List<CardItemDtoInter> findAuthorsCardItemById(@Param("userId") Long userId);

    @Query("""
                SELECT 
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.favorites f WHERE f.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.buyers b WHERE b.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM Game g
                WHERE EXISTS (
                    SELECT 1 FROM g.buyers b WHERE b.id = :userId
                )
            """)
    Set<CardItemDtoInter> findBuyersCardItemById(@Param("userId") Long userId);

    @Query("""
                SELECT 
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.favorites f WHERE f.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM g.buyers b WHERE b.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM Game g
                WHERE EXISTS (
                    SELECT 1 FROM g.favorites f WHERE f.id = :userId
                )
            """)
    Set<CardItemDtoInter> findFavoritesCardItemById(@Param("userId") Long userId);

}
