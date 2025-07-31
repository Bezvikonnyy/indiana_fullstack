package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.entity.Cart;

public interface CRUDCartService {

    Cart getCart(Long id);

    void clearCart(Long id);
}
