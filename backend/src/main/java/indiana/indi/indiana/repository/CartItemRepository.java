package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
