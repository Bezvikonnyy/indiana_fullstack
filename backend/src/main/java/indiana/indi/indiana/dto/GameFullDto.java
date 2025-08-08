package indiana.indi.indiana.dto;

import java.math.BigDecimal;
import java.util.List;

public record GameFullDto(
        Long id,
        String title,
        String details,
        String imageUrl,
        String gameFileUrl,
        Long authorId,
        List<CategoryForGameDto> categories,
        BigDecimal price) {
}
