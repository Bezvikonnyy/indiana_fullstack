package indiana.indi.indiana.mapper.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper mapper;

    public OrderDto toDto(OrderDtoInter orderDtoInter, List<OrderItemDtoInter> itemDtoInters) {
        return new OrderDto(
                orderDtoInter.getId(),
                orderDtoInter.getUserId(),
                itemDtoInters.stream().map(mapper::toDto).toList(),
                orderDtoInter.getTotalPrice(),
                orderDtoInter.getStatus(),
                orderDtoInter.getCreatedAt()
        );
    }
}
