package indiana.indi.indiana.dto.cartAndPay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LiqPayCallbackDto(
        @JsonProperty("order_id") String orderId,
        @JsonProperty("payment_id") String paymentId,
        @JsonProperty("status") String status,
        @JsonProperty("transaction_id") String transactionId,
        @JsonProperty("amount") String amount,
        @JsonProperty("currency") String currency
) {}
