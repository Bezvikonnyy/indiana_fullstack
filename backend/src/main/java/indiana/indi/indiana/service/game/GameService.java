package indiana.indi.indiana.service.game;

import indiana.indi.indiana.entity.Comment;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Iterable<Game> findAllGames(String filter);

    Game createGame(
            String title,
            String details,
            String imageUrl,
            String gameFileUrl,
            List<Long> categoryId,
            List<Comment> comments,
            User author);

    Optional<Game> findGame(Long gameId);

    void editGame(
            Long id,
            String title,
            String details,
            String imageUrl,
            String gameFileUrl,
            List<Long> categoryId
    );

    void deleteGame(Long id);
}
