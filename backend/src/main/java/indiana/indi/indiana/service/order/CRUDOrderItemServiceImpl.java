package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderItemPayload;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.cartAndPay.OrderItem;
import indiana.indi.indiana.repository.cartAndPay.OrderItemRepository;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDOrderItemServiceImpl implements CRUDOrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;

    @Override
    public OrderItem createOrderItem(NewOrderItemPayload payload) {
        Order order = orderRepository.getReferenceById(payload.orderId());
        Game game = gameRepository.getReferenceById(payload.gameId());
        OrderItem orderItem = OrderItem.builder().order(order).game(game).price(game.getPrice()).quantity(1).build();
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found."));
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
