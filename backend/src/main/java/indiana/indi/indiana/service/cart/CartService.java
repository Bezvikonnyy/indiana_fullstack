package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.entity.cartAndPay.Cart;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.users.User;

public interface CartService {

    Cart getCart(Long cartId);

    Cart addCartItem(Long cartId, CartItemPayload payload);

    Cart removeCartItem(Long cartId, CartItemPayload payload);

    Cart cleanCart(Long id);

    Order toOrder(User user, NewOrderPayload payload);
}
