package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CommentDto;
import indiana.indi.indiana.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getAuthor().getUsername(),
                comment.getCreatedAt(),
                comment.getGame().getId());
        return dto;
    }
}
