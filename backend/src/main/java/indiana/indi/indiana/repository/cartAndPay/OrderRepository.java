package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.dtoInterface.cartAndPay.OrderDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("""
            UPDATE Order o
            SET o.status =:orderStatus
            WHERE o.id =:orderId 
            """)
    void updateOrder(@Param("orderId") Long orderId, OrderStatus orderStatus);

    @Query("""
            SELECT
                o.id as id,
                o.user.id as userId,
                o.totalAmount as totalPrice,
                o.status as status,
                o.createdAt as createdAt
            FROM Order o
            WHERE o.id =:orderId
            """)
    Optional<OrderDtoInter> getOrderById(@Param("orderId") Long orderId);

    @Query("""
            SELECT 
                i.id as id,
                i.game.id as gameId,
                i.game.title as gameTitle,
                i.game.price as price
            FROM OrderItem i
            WHERE i.order.id =:orderId
            """)
    List<OrderItemDtoInter> getOrderItemByOrderId(Long orderId);
}
