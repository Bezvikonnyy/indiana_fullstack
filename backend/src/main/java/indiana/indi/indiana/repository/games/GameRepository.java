package indiana.indi.indiana.repository.games;

import indiana.indi.indiana.dtoInterface.categories.CategoryForGameDtoInter;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameDetailsDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameForProfileDtoInter;
import indiana.indi.indiana.entity.games.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
            FROM Game g
            WHERE g.id = :gameId
            """)
    Optional<CardItemDtoInter> getGameById(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.details as details,
                    g.imageUrl as imageUrl,
                    g.gameFileUrl as gameFileUrl,
                    g.author.id as authorId,
                    g.price as price,
                    CASE
                        WHEN :userId IS NOT NULL AND EXISTS (
                            SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                        ) THEN true
                        ELSE false
                    END as isFavorite,
                    CASE
                        WHEN :userId IS NOT NULL AND EXISTS (
                            SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                        ) THEN true
                        ELSE false
                    END as isInCart,
                    CASE
                        WHEN :userId IS NOT NULL AND EXISTS (
                            SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                        ) THEN true
                        ELSE false
                    END as isPurchased
                FROM Game g
                WHERE g.id = :gameId
            """)
    Optional<GameDetailsDtoInter> getGameDetailsById(
            @Param("userId") Long userId,
            @Param("gameId") Long gameId
    );

    @Query("""
                SELECT c.id as id, c.title as title
                FROM GameCategory gc
                JOIN gc.category c
                WHERE gc.game.id = :gameId
            """)
    Set<CategoryForGameDtoInter> getCategoriesForGameDetails(@Param("gameId") Long gameId);

    @Query("""
            SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game.id = g.id AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game.id = g.id AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game.id = g.id AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
            FROM GameCategory gc
                JOIN gc.game g
                WHERE gc.category.id = :categoryId
                """)
    List<CardItemDtoInter> findAllByCategoryWithUserStatus(
            @Param("categoryId") Long categoryId,
            @Param("userId") Long userId
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
            FROM UserPurchasedGames up
            JOIN up.game g
            WHERE up.user.id=:userId
            """)
    Set<GameForProfileDtoInter> findBuyersGameById(@Param("userId") Long userId);

    @Query("""
            SELECT
                g.id as id,
                g.title as title
            FROM UserFavoriteGames uf
            JOIN uf.game g
            WHERE uf.user.id=:userId
            """)
    Set<GameForProfileDtoInter> findFavoritesGameById(@Param("userId") Long userId);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
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
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up2 WHERE up2.game = g AND up2.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM UserPurchasedGames up
                JOIN up.game g
                WHERE up.user.id = :userId
            """)
    Set<CardItemDtoInter> findBuyersCardItemById(@Param("userId") Long userId);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf2 WHERE uf2.game = g AND uf2.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM UserFavoriteGames uf
                JOIN uf.game g
                WHERE uf.user.id =:userId
            """)
    Set<CardItemDtoInter> findFavoritesCardItemById(@Param("userId") Long userId);

    @Query("""
            SELECT
                g.id as id,
                g.title as title,
                g.imageUrl as imageUrl,
                g.price as price,
                CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
            FROM Game g
            """)
    Page<CardItemDtoInter> getGameByHome(@Param("userId") Long userId, Pageable pageable);
}
