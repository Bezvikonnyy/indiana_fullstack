package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.dto.comments.CommentDto;
import indiana.indi.indiana.entity.comments.Comment;
import indiana.indi.indiana.mapper.comments.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentForControllerService {

    private final CRUDCommentsServiceImpl service;

    private final CommentMapper mapper;

    public CommentDto createComment(CommentPayload payload, Long userId) {
        Comment comment = service.createComment(payload,userId);
        return mapper.toCommentDto(comment);
    }

    public CommentDto editComment(Long commentId, CommentPayload payload, Long userId) {
        Comment comment = service.editComment(commentId,payload,userId);
        return mapper.toCommentDto(comment);
    }

    public CommentDto getComment(Long commentId) {
        Comment comment = service.getComment(commentId);
        return mapper.toCommentDto(comment);
    }

    public List<CommentDto> getComments(Long commentId) {
        List<Comment> comments = service.getComments(commentId);
        return comments.stream().map(mapper::toCommentDto).toList();
    }

    public void deleteComment(Long commentId, Long userId) {
        service.deleteComment(commentId, userId);
    }
}
