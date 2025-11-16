package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.entity.comments.Comment;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.repository.comments.CommentRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDCommentsServiceImpl implements CRUDCommentsService {

    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final CommentService service;

    @Override
    public Comment createComment(CommentPayload payload, Long authorId) {
        Comment comment = new Comment();
        comment.setText(payload.text());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!")));
        comment.setGame(gameRepository.findById(payload.gameId())
                .orElseThrow(() -> new EntityNotFoundException("Game not found!")));
        return repository.save(comment);
    }

    @Override
    public Comment editComment(Long commentId, CommentPayload payload, Long currentUserId) {
        Comment comment = service.findComment(commentId);

        if (!currentUserId.equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("You are not the author of this comment");
        }

        comment.setText(payload.text());
        return repository.save(comment);
    }

    @Override
    public Comment getComment(Long commentId) {
        return service.findComment(commentId);
    }

    @Override
    public List<Comment> getComments(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found."));
        return game.getComments().stream().toList();
    }

    @Override
    public void deleteComment(Long commentId, Long currentUserId) {
        Comment comment = service.findComment(commentId);
        if (!currentUserId.equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("You are not the author of this comment");
        }
        repository.delete(comment);
    }
}
