package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.mapper.cartAndPay.OrderMapper;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartForControllerService {

    private final CartServiceImpl cartService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public CartDto getCart(Long userId) {
        return cartService.getCart(userId);
    }

    @Transactional
    public CartDto addCartItem(CartItemPayload payload, Long userId){
        return cartService.addCartItem(payload, userId);
    }

    @Transactional
    public CartDto removeCartItem(CartItemPayload payload, Long userId){
        return cartService.removeCartItem(payload, userId);
    }

    @Transactional
    public CartDto clearCart(Long userId) {
        return cartService.cleanCart(userId);
    }

    @Transactional
    public OrderDto toOrder(Long userId){
        Order order = cartService.toOrder(userId);
        OrderDtoInter orderDtoInter = orderRepository.getOrderById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        List<OrderItemDtoInter> itemDtoInters = orderRepository.getOrderItemByOrderId(order.getId());
        return orderMapper.toDto(orderDtoInter, itemDtoInters);
    }

    public OrderDto paymentMethod(Long orderId) {
        OrderDtoInter orderDtoInter = orderRepository.getOrderById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        List<OrderItemDtoInter> itemDtoInters = orderRepository.getOrderItemByOrderId(orderId);
        return orderMapper.toDto(orderDtoInter, itemDtoInters);
    }
}
