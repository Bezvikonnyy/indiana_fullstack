package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.dto.CommentDto;
import indiana.indi.indiana.entity.Comment;
import indiana.indi.indiana.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentForControllerService {

    private final CRUDCommentsServiceImpl service;

    private final CommentMapper mapper;

    public CommentDto createComment(CommentPayload payload, Long id) {
        Comment comment = service.createComment(payload,id);
        return mapper.toCommentDto(comment);
    }

    public CommentDto editComment(Long id, CommentPayload payload, Long userId) throws AccessDeniedException {
        Comment comment = service.editComment(id,payload,userId);
        return mapper.toCommentDto(comment);
    }

    public CommentDto getComment(Long id) {
        Comment comment = service.getComment(id);
        return mapper.toCommentDto(comment);
    }

    public List<CommentDto> getComments(Long id) {
        List<Comment> comments = service.getComments(id);
        return comments.stream().map(mapper::toCommentDto).toList();
    }

    public void deleteComment(Long id, Long userId) throws AccessDeniedException {
        service.deleteComment(id, userId);
    }
}
