package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CRUDOrderServiceImpl implements CRUDOrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(User user, NewOrderPayload payload) {
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .totalAmount(BigDecimal.ZERO)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
