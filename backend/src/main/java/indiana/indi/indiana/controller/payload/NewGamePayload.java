package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.entity.Comment;

import java.util.List;

public record NewGamePayload(
        String title,
        String details,
        String imageUrl,
        String gameFileUrl,
        List<Long> categoryId,
        List<Comment> comments) {
}
