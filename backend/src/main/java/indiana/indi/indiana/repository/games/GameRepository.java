package indiana.indi.indiana.repository.games;

import indiana.indi.indiana.dtoInterface.games.*;
import indiana.indi.indiana.entity.games.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
            SELECT
                g.id as id,
                g.title as title,
                g.imageUrl as imageUrl,
                g.price as price,
                d.discountPercent as discountPercent,
                g.averageRating as rating,
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
            LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
            WHERE g.id = :gameId
            """)
    Optional<CardItemDtoInter> getGameById(@Param("userId") Long userId,
                                           @Param("gameId") Long gameId,
                                           @Param("now") LocalDateTime now);


    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.details as details,
                    g.imageUrl as imageUrl,
                    g.gameFileUrl as gameFileUrl,
                    g.author.id as authorId,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
                    CASE WHEN :userId IS NOT NULL AND EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN :userId IS NOT NULL AND EXISTS (
                            SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN :userId IS NOT NULL AND EXISTS (
                            SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM Game g
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE g.id = :gameId
            """)
    Optional<GameDetailsDtoInter> getGameDetailsById(@Param("userId") Long userId,
                                                     @Param("gameId") Long gameId,
                                                     @Param("now") LocalDateTime now);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
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
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE g.author.id = :userId
            """)
    List<CardItemDtoInter> findAuthorsCardItemById(@Param("userId") Long userId,
                                                   @Param("now") LocalDateTime now);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
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
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE up.user.id = :userId
            """)
    Set<CardItemDtoInter> findBuyersCardItemById(@Param("userId") Long userId,
                                                 @Param("now") LocalDateTime now);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
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
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE uf.user.id =:userId
            """)
    Set<CardItemDtoInter> findFavoritesCardItemById(@Param("userId") Long userId,
                                                    @Param("now") LocalDateTime now);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased,
                    gc.category.id as categoryId
                FROM GameCategory gc
                JOIN gc.game g
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE gc.category.id IN :categoryIds
                ORDER BY g.createdAt DESC
            """)
    List<CardItemDtoInter> findGamesByCategory(@Param("userId") Long userId,
                                               @Param("categoryIds") List<Long> categoryIds,
                                               @Param("now") LocalDateTime now);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM GameCategory gc
                JOIN gc.game g
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                WHERE gc.category.id = :categoryId
            """)
    Page<CardItemDtoInter> findByCategoryId(@Param("userId") Long userId,
                                            @Param("categoryId") Long categoryId,
                                            @Param("now") LocalDateTime now,
                                            Pageable pageable);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
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
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                ORDER BY g.createdAt DESC
            """)
    Page<CardItemDtoInter> findLatestGames(@Param("userId") Long userId,
                                           @Param("now") LocalDateTime now, Pageable pageable);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
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
                LEFT JOIN GameDiscount d ON d.game = g AND d.active = true AND :now BETWEEN d.startDate AND d.endDate
                ORDER BY g.purchasesCount DESC
            """)
    Page<CardItemDtoInter> findPopularGames(@Param("userId") Long userId,
                                            @Param("now") LocalDateTime now, Pageable pageable);

    @Query("""
                SELECT
                    g.id as id,
                    g.title as title,
                    g.imageUrl as imageUrl,
                    g.price as price,
                    d.discountPercent as discountPercent,
                    g.averageRating as rating,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserFavoriteGames uf WHERE uf.game = g AND uf.user.id = :userId
                    ) THEN true ELSE false END as isFavorite,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM CartItem ci WHERE ci.game = g AND ci.cart.user.id = :userId
                    ) THEN true ELSE false END as isInCart,
                    CASE WHEN EXISTS (
                        SELECT 1 FROM UserPurchasedGames up WHERE up.game = g AND up.user.id = :userId
                    ) THEN true ELSE false END as isPurchased
                FROM GameDiscount d
                JOIN d.game g
                WHERE d.active = true AND :now BETWEEN d.startDate AND d.endDate
            """)
    Page<CardItemDtoInter> findDiscountedGames(@Param("userId") Long userId,
                                               @Param("now") LocalDateTime now, Pageable pageable);

}
