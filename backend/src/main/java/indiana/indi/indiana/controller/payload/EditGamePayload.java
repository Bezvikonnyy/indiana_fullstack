package indiana.indi.indiana.controller.payload;

import java.math.BigDecimal;
import java.util.List;

public record EditGamePayload(
        String title,
        String details,
        List<Long> categoryId,
        BigDecimal price) {
}
