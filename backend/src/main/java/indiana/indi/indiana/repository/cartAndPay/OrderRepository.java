package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.entity.cartAndPay.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
