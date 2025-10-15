package indiana.indi.indiana.dto.cartAndPay;

public record StringDataDto(
        String version,
        String publicKey,
        String action,
        String amount,
        String currency,
        String description,
        String orderId,
        String paymentId,
        String sandBox,
        String serverUrl,
        String resultUrl) {}
