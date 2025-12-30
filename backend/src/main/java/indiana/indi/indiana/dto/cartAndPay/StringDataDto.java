package indiana.indi.indiana.dto.cartAndPay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StringDataDto(

        @JsonProperty("public_key") String publicKey,
        @JsonProperty("version") Integer version,
        @JsonProperty("action") String action,
        @JsonProperty("amount") String amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("description") String description,
        @JsonProperty("order_id") String orderId,
        @JsonProperty("sandbox") Integer sandbox,
        @JsonProperty("server_url") String serverUrl,
        @JsonProperty("result_url") String resultUrl
) {}
