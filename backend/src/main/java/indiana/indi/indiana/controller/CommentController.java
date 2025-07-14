package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.dto.CommentDto;
import indiana.indi.indiana.mapper.CommentMapper;
import indiana.indi.indiana.service.comments.CRUDCommentsServiceImpl;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentMapper mapper;

    private final CRUDCommentsServiceImpl service;

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable("commentId") long commentId) {
        return mapper.toCommentDto(service.getComment(commentId));
    }

    @GetMapping("/{gameId}/comments")
    public List<CommentDto> getComments(@PathVariable("gameId") long gameId) {
        return service.getComments(gameId);
    }

    @PostMapping("/create_comment")
    public CommentDto createComment(@Valid @RequestBody CommentPayload payload,
                                    @AuthenticationPrincipal CustomUserDetails author) {
        return mapper.toCommentDto(service.createComment(payload, author.getId()));
    }

    @PutMapping("/edit_comment/{commentId}")
    public CommentDto editComment(@Valid @RequestBody CommentPayload payload,
                                  @PathVariable("commentId") long commentId,
                                  @AuthenticationPrincipal CustomUserDetails author) throws AccessDeniedException {
        return mapper.toCommentDto(service.editComment(commentId, payload, author.getId() ));
    }

    @DeleteMapping("/delete_comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") long commentId,
                              @AuthenticationPrincipal CustomUserDetails author) throws AccessDeniedException{
        service.deleteComment(commentId, author.getId());
    }
}
