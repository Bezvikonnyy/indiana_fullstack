package indiana.indi.indiana.service.comments;

import indiana.indi.indiana.entity.Comment;
import indiana.indi.indiana.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public Comment findComment(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found!"));
    }
}
