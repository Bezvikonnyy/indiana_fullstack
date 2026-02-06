package indiana.indi.indiana.dto.comments;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String text,
        Long authorId,
        String authorName,
        LocalDateTime time,
        Long gameId) {}
