package indiana.indi.indiana.dto.cartAndPay;

public record LiqPayCallbackDto(
        String orderId,
        String paymentId,
        String status,
        String transactionId,
        String amount,
        String currency) {
}
