package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.controller.payload.CommentPayload;
import indiana.indi.indiana.dto.CommentDto;
import indiana.indi.indiana.entity.Comment;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.mapper.CommentMapper;
import indiana.indi.indiana.repository.CommentRepository;
import indiana.indi.indiana.repository.GameRepository;
import indiana.indi.indiana.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDCommentsServiceImpl implements CRUDCommentsService{

    private final CommentRepository repository;

    private final UserRepository userRepository;

    private final GameRepository gameRepository;

    private final CommentService service;

    private final CommentMapper mapper;

    @Override
    public Comment createComment(CommentPayload payload,Long authorId) {
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
    public Comment editComment(Long id, CommentPayload payload, Long currentUserId) throws AccessDeniedException {
        Comment comment = service.findComment(id);

        if (!currentUserId.equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("You are not the author of this comment");
        }

        comment.setText(payload.text());
        return repository.save(comment);
    }


    @Override
    public Comment getComment(Long id) {
        return service.findComment(id);
    }

    @Override
    public List<CommentDto> getComments(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game not found."));
        return game.getComments().stream().map(com -> mapper.toCommentDto(com)).toList();
    }

    @Override
    public void deleteComment(Long id, Long currentUserId) throws AccessDeniedException {
        Comment comment = service.findComment(id);
        if(!currentUserId.equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("You are not the author of this comment");
        }
        repository.delete(comment);
    }
}
