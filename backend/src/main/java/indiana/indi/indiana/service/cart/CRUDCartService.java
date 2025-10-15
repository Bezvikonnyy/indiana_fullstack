package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.dto.cartAndPay.CartDto;

public interface CRUDCartService {

    CartDto getCart(Long id);

    void clearCart(Long id);
}
