package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.cartAndPay.Payment;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.enums.PaymentStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);

    Optional<Payment> findByOrderId(Long orderId);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Payment p
            SET p.status =:paymentStatus
            WHERE p.id =:paymentId 
            """)
    void updatePayment(@Param("paymentId") Long paymentId, PaymentStatus paymentStatus);
}
