package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderItemPayload;
import indiana.indi.indiana.entity.cartAndPay.OrderItem;

public interface CRUDOrderItemService {

    OrderItem createOrderItem(NewOrderItemPayload payload);

    OrderItem getOrderItem(Long id);

    void deleteOrderItem(Long id);
}
