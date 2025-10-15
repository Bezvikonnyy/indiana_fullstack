package indiana.indi.indiana.service.order;

import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.mapper.cartAndPay.OrderMapper;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDOrderServiceImpl implements CRUDOrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper mapper;

    @Override
    public Order createOrder(Long userId) {
        User user = userRepository.getReferenceById(userId);
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderStatus orderStatus) {
        orderRepository.updateOrder(orderId, orderStatus);
        OrderDtoInter orderDtoInter = orderRepository.getOrderById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        List<OrderItemDtoInter> itemDtoIter = orderRepository.getOrderItemByOrderId(orderId);
        return mapper.toDto(orderDtoInter, itemDtoIter);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        OrderDtoInter orderDtoInter = orderRepository.getOrderById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        List<OrderItemDtoInter> itemDtoInters = orderRepository.getOrderItemByOrderId(orderId);
        return mapper.toDto(orderDtoInter, itemDtoInters);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
