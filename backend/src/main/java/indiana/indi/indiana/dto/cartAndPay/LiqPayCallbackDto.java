package indiana.indi.indiana.dto.cartAndPay;

public record LiqPayCallbackDto(String orderId, String status, String transactionId, String amount, String currency) {
}
