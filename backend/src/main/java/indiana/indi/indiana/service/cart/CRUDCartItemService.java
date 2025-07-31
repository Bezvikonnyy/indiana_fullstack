package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.entity.CartItem;

public interface CRUDCartItemService {

    CartItem addCartItem(CartItemPayload payload, Long id);

    void removeCartItem(CartItemPayload payload, Long id);

    CartItem getCartItem(Long id);
}
