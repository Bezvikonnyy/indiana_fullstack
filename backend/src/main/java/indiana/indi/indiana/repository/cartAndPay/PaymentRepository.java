package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.cartAndPay.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
}
