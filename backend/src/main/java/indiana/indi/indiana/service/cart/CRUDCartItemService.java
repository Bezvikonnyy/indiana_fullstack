package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.entity.cartAndPay.CartItem;
import org.springframework.transaction.annotation.Transactional;

public interface CRUDCartItemService {

    @Transactional
    void addCartItem(CartItemPayload payload, Long id);

    @Transactional
    void removeCartItem(CartItemPayload payload, Long id);

}
