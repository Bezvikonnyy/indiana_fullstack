package indiana.indi.indiana.service.order;

import indiana.indi.indiana.dto.cartAndPay.OrderDto;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.enums.OrderStatus;

public interface CRUDOrderService {

    Order createOrder(Long userId);

    OrderDto updateOrder(Long userId, OrderStatus status);

    OrderDto getOrder(Long userId);

    void deleteOrder(Long userId);
}
