package indiana.indi.indiana.service.payment;

import indiana.indi.indiana.dto.cartAndPay.PaymentRequestDto;

public interface PaymentStrategy {
    PaymentRequestDto createPayment(Long orderId);
    void processCallback(String data, String signature);
}
