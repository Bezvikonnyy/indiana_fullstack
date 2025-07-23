package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.entity.Comment;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CRUDCommentsService {

    Comment createComment(CommentPayload payload, Long authorId);

    Comment editComment(Long id, CommentPayload payload, Long currentUserId) throws AccessDeniedException;

    Comment getComment(Long id);

    List<Comment> getComments(Long id);

    void deleteComment(Long id, Long currentUserId) throws AccessDeniedException;
}
