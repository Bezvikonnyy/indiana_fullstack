package indiana.indi.indiana.dto.news;

import java.time.LocalDateTime;

public record NewsDto(
        String title,
        String content,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
