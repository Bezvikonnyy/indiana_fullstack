package indiana.indi.indiana.dto;

public record LiqPayCallbackDto(String orderId, String status, String transactionId, String amount, String currency) {
}
