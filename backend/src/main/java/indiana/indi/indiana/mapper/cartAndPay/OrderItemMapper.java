package indiana.indi.indiana.mapper.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.OrderItemDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    
    public OrderItemDto toDto(OrderItemDtoInter dtoInter) {
        return new OrderItemDto(dtoInter.getId(), dtoInter.getGameId(), dtoInter.getGameTitle(), dtoInter.getPrice());
    }
}
