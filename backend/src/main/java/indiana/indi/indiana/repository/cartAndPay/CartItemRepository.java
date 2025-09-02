package indiana.indi.indiana.repository.cartAndPay;

import indiana.indi.indiana.entity.cartAndPay.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
