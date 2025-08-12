package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.entity.*;
import indiana.indi.indiana.repository.CartRepository;
import indiana.indi.indiana.repository.OrderRepository;
import indiana.indi.indiana.service.order.CRUDOrderServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CRUDCartServiceImpl cartService;

    private final CRUDCartItemServiceImpl cartItemService;

    private final CartRepository cartRepository;

    private final CRUDOrderServiceImpl orderService;

    private final OrderRepository orderRepository;

    @Override
    public Cart getCart(Long userId) {
        return cartService.getCart(userId);
    }

    @Override
    public Cart addCartItem(Long userId, CartItemPayload payload) {
        Cart cart = cartService.getCart(userId);
        cartItemService.addCartItem(payload,userId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeCartItem(Long userId, CartItemPayload payload) {
        Cart cart = cartService.getCart(userId);
        cartItemService.removeCartItem(payload,userId);
        return cart;
    }

    @Override
    public Cart cleanCart(Long userId) {
        Cart cart = cartService.getCart(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Order toOrder(User user, NewOrderPayload payload) {
        Cart cart = cartService.getCart(user.getId());
        List<CartItem> cartItems = cart.getItems();

        Order order = orderService.createOrder(user, payload);  // Лучше изменить этот метод, чтобы принимал User, а не userId из payload
        order.setUser(user);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    Game game = cartItem.getGame();
                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .game(game)
                            .price(game.getPrice())
                            .quantity(1)
                            .build();
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

}
