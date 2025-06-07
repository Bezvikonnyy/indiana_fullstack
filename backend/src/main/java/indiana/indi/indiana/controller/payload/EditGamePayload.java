package indiana.indi.indiana.controller.payload;

import java.util.List;

public record EditGamePayload(
        String title,
        String details,
        List<Long> categoryId) {
}
