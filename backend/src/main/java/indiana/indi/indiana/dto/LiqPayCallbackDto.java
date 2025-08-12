package indiana.indi.indiana.dto;

public record LiqPayCallbackDto(String order_id, String status, String transaction_id, String amount, String currency) {
}
