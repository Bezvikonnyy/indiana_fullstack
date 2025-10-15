package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.entity.cartAndPay.Order;

public interface CartService {

    CartDto getCart(Long cartId);

    CartDto addCartItem(CartItemPayload payload, Long userId);

    CartDto removeCartItem(CartItemPayload payload, Long cartId);

    CartDto cleanCart(Long userId);

    Order toOrder(Long userId);
}
