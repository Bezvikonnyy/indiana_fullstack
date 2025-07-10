package indiana.indi.indiana.dto;

import java.util.List;

public record GameFullDto(
        Long id,
        String title,
        String details,
        String imageUrl,
        String gameFileUrl,
        Long authorId,
        List<CategoryForGameDto> categories) {
}
