package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.cartAndPay.OrderItem;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.service.order.CRUDOrderServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CRUDCartServiceImpl cartService;
    private final CRUDCartItemServiceImpl cartItemService;
    private final CartRepository cartRepository;
    private final CRUDOrderServiceImpl orderService;
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;

    @Override
    public CartDto getCart(Long userId) {
        return cartService.getCart(userId);
    }

    @Override
    @Transactional
    public CartDto addCartItem(CartItemPayload payload, Long userId) {
        cartItemService.addCartItem(payload, userId);
        return cartService.getCart(userId);
    }

    @Override
    @Transactional
    public CartDto removeCartItem(CartItemPayload payload, Long userId) {
        cartItemService.removeCartItem(payload,userId);
        return cartService.getCart(userId);
    }

    @Override
    @Transactional
    public CartDto cleanCart(Long userId) {
        cartService.clearCart(userId);
        return cartService.getCart(userId);
    }

    @Override
    @Transactional
    public Order toOrder(Long userId) {
        Order order = orderService.createOrder(userId);
        Set<CartItemDtoInter> cartItems = cartRepository.getCartItemsByUserId(userId);
        List<OrderItem> orderItems = cartItems.stream().map(
                cartItem -> {
                    Game game = gameRepository.getReferenceById(cartItem.getGameId());
                    return OrderItem.builder()
                            .order(order)
                            .game(game)
                            .price(cartItem.getPrice())
                            .quantity(1)
                            .build();
                }).collect(Collectors.toList());
        order.setItems(orderItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

}
