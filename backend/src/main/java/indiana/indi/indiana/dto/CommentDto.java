package indiana.indi.indiana.dto;

import java.time.LocalDateTime;

public record CommentDto(Long id, String text, Long authorId, String authorName, LocalDateTime time, Long gameId) {
}
