package indiana.indi.indiana.mapper.comments;

import indiana.indi.indiana.dto.comments.CommentDto;
import indiana.indi.indiana.entity.comments.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getAuthor().getUsername(),
                comment.getCreatedAt(),
                comment.getGame().getId());
    }
}
