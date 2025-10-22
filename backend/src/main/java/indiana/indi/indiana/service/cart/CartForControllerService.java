package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.dto.cartAndPay.OrderStatusDto;
import indiana.indi.indiana.dto.cartAndPay.PaymentRequestDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.mapper.cartAndPay.OrderMapper;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.service.payment.PaymentStrategy;
import indiana.indi.indiana.service.payment.PaymentStrategyFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartForControllerService {

    private final CartServiceImpl cartService;
    private final PaymentStrategyFactory paymentStrategy;
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

    public PaymentRequestDto createPayment(String paymentMethod, Long orderId){
        PaymentStrategy strategy = paymentStrategy.getStrategy(paymentMethod);
        return strategy.createPayment(orderId);
    }

    public void callbackPayment(String paymentMethod, String data, String signature){
        PaymentStrategy strategy = paymentStrategy.getStrategy(paymentMethod);
        strategy.processCallback(data, signature);
    }

    public OrderStatusDto getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return new OrderStatusDto(order.getId(), order.getStatus().toString());
    }
}
