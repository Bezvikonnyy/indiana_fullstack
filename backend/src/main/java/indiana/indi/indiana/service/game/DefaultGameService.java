package indiana.indi.indiana.service.game;

import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Comment;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.CategoryRepository;
import indiana.indi.indiana.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultGameService implements GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Iterable<Game> findAllGames(String filter) {
        if(filter != null && !filter.isBlank()){
            return gameRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return gameRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Game createGame(
            String title,
            String details,
            String imageUrl,
            String gameFileUrl,
            List<Long> categoryId,
            List<Comment> comments,
            User author){
        List<Category> categories = categoryRepository.findAllById(categoryId);

        if(categories.isEmpty()){
            throw  new RuntimeException("Category not found!");
        }

        return this.gameRepository.save(new Game(
                null,title, details, imageUrl, gameFileUrl, categories, comments, author
        ));
    }

    @Override
    public Optional<Game> findGame(Long gameId) { return this.gameRepository.findById(gameId);}

    @Override
    @Transactional
    public void editGame(
            Long id,
            String title,
            String details,
            String imageUrl,
            String gameFileUrl,
            List<Long> categoryId
    ) {
        List<Category> categories = categoryRepository.findAllById(categoryId);

        this.gameRepository.findById(id).ifPresentOrElse(
                game -> {
                    game.setTitle(title);
                    game.setDetails(details);
                    game.setImageUrl(imageUrl);
                    game.setGameFileUrl(gameFileUrl);
                    game.setCategories(categories);

                    gameRepository.save(game);
                },
                () -> {
                    throw new RuntimeException("Game not found with id " + id);
                }
        );
    }

    @Override
    @Transactional
    public void deleteGame(Long id) {
        this.gameRepository.deleteById(id);}
}
