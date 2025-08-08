package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.dto.CartDto;
import indiana.indi.indiana.dto.OrderDto;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.CartMapper;
import indiana.indi.indiana.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartForControllerService {

    private final CartServiceImpl cartService;

    private final CartMapper cartMapper;

    private final OrderMapper orderMapper;

    public CartDto getCart(Long userId) {
        return cartMapper.toDto(cartService.getCart(userId));
    }

    public CartDto addCartItem(Long userId, CartItemPayload payload){
        return cartMapper.toDto(cartService.addCartItem(userId,payload));
    }

    public CartDto removeCartItem(Long userId, CartItemPayload payload){
        return cartMapper.toDto(cartService.removeCartItem(userId,payload));
    }

    public CartDto clearCart(Long userId) {
        return cartMapper.toDto(cartService.cleanCart(userId));
    }

    public OrderDto toOrder(Long userId, User user, NewOrderPayload payload){
        return orderMapper.toDto(cartService.toOrder(userId, user, payload));
    }
}
