package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.entity.Order;
import indiana.indi.indiana.enums.OrderStatus;

public interface CRUDOrderService {

    Order createOrder(NewOrderPayload payload);

    Order updateOrder(Long id, OrderStatus status);

    Order getOrder(Long id);

    void deleteOrder(Long id);
}
