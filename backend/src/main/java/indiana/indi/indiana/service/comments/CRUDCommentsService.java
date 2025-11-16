package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.entity.comments.Comment;

import java.util.List;

public interface CRUDCommentsService {

    Comment createComment(CommentPayload payload, Long authorId);

    Comment editComment(Long commentId, CommentPayload payload, Long currentUserId);

    Comment getComment(Long commentId);

    List<Comment> getComments(Long commentId);

    void deleteComment(Long id, Long currentUserId);
}
