package indiana.indi.indiana.mapper.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.dto.cartAndPay.OrderItemDto;
import indiana.indi.indiana.entity.cartAndPay.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDto toDto(Order order) {
        BigDecimal totalPrice = order.getItems().stream()
                .map(item -> item.getGame().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getItems().stream().map(item -> new OrderItemDto(
                        item.getId(),
                        item.getGame().getId(),
                        item.getGame().getTitle(),
                        item.getPrice(),
                        item.getQuantity())).collect(Collectors.toList()),
                totalPrice,
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}
