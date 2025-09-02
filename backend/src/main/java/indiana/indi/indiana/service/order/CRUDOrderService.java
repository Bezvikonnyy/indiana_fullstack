package indiana.indi.indiana.service.order;

import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.enums.OrderStatus;

public interface CRUDOrderService {

    Order createOrder(User user, NewOrderPayload payload);

    Order updateOrder(Long id, OrderStatus status);

    Order getOrder(Long id);

    void deleteOrder(Long id);
}
