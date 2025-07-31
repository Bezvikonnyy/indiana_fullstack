package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
