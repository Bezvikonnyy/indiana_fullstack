package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.dtoInterface.cartAndPay.CartDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Cart;
import indiana.indi.indiana.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("""
            SELECT
                c.id as id,
                c.user.id as userId,
                size(c.items) as totalItems
            FROM Cart c
            WHERE c.user.id =:userId
            """)
    Optional<CartDtoInter> getCartByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT
                 i.id as id,
                 i.game.id as gameId,
                 i.game.title as gameTitle,
                 i.game.price as price,
                 i.game.imageUrl as imageUrl
            FROM CartItem i
            WHERE i.cart.user.id =:userId
            """)
    Set<CartItemDtoInter> getCartItemsByUserId(@Param("userId") Long userId);

    Optional<Cart> findByUser(User user);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM CartItem i WHERE i.cart.user.id =:userId
            """)
    void clearCartByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM CartItem i
            WHERE i.cart.id =:userId AND i.game.id =:gameId
            """)
    void removeCartItemByUserAndGame(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END
            FROM CartItem i
            WHERE i.cart.id = :cartId AND i.game.id = :gameId
            """)
    boolean existsByCartIdAndGameId(@Param("cartId") Long cartId, @Param("gameId") Long gameId);

}
