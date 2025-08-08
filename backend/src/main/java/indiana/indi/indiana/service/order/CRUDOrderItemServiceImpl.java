package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderItemPayload;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.Order;
import indiana.indi.indiana.entity.OrderItem;
import indiana.indi.indiana.repository.OrderItemRepository;
import indiana.indi.indiana.repository.OrderRepository;
import indiana.indi.indiana.service.game.GameService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDOrderItemServiceImpl implements CRUDOrderItemService{

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository orderRepository;

    private final GameService gameService;

    @Override
    public OrderItem createOrderItem(NewOrderItemPayload payload) {
        Order order = orderRepository.findById(payload.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        Game game = gameService.getGame(payload.gameId());
        OrderItem orderItem = OrderItem.builder().order(order).game(game).price(game.getPrice()).quantity(1).build();
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItem(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("OrderItem not found."));
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found."));
        orderItemRepository.delete(orderItem);
    }
}
