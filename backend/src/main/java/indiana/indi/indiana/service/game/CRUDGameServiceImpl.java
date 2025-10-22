package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.manyToManyEntities.GameCategory;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.manyToMany.GameCategoryRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import indiana.indi.indiana.service.categories.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CRUDGameServiceImpl implements CRUDGameService {

    private final GameRepository gameRepository;
    private final GameCategoryRepository gameCategoryRepository;
    private final UserRepository userRepository;
    private final CategoryServiceImpl categoryService;
    private final FileService fileService;

    @Override
    @Transactional
    public Game createGame(
            NewGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            Long authorId
    ) {
        String imageFileUrl = fileService.saveFile(imageFile, "imageFile");
        String gameFileUrl = fileService.saveFile(gameFile, "gameFile");

        List<Category> categories = categoryService.validCategoryByGame(payload.categoryId());
        User author = userRepository.getReferenceById(authorId);

        Game game = Game.builder()
                .title(payload.title())
                .details(payload.details())
                .imageUrl(imageFileUrl)
                .gameFileUrl(gameFileUrl)
                .author(author)
                .price(payload.price())
                .build();

        gameRepository.save(game);

        List<GameCategory> links = categories.stream()
                .map(category -> new GameCategory(null, game, category)).toList();
        gameCategoryRepository.saveAll(links);
        return game;
    }

    @Override
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game ID " + gameId + " not found."));
    }

    @Override
    @Transactional
    public Game editGame(
            Long gameId,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            Long userId
    ) {
        Game existingGame = getGameById(id);

        boolean isAdmin = userDetails.isAdmin();
        boolean isAuthor = Objects.equals(existingGame.getAuthor().getId(), userDetails.getId());

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("Access denied.");
        }

        List<Category> categories = categoryService.validCategoryByGame(payload.categoryId());


        if (imageFile != null && !imageFile.isEmpty()) {
            fileService.deleteFileIfExists(existingGame.getImageUrl());
            String newImageFileUrl = fileService.saveFile(imageFile, "imageFile");
            existingGame.setImageUrl(newImageFileUrl);
        }

        if (gameFile != null && !gameFile.isEmpty()) {
            fileService.deleteFileIfExists(existingGame.getGameFileUrl());
            String newGameFileUrl = fileService.saveFile(gameFile, "gameFile");
            existingGame.setGameFileUrl(newGameFileUrl);
        }

        existingGame.setTitle(payload.title());
        existingGame.setDetails(payload.details());
        existingGame.setCategories(categories);
        existingGame.setPrice(payload.price());

        return gameRepository.save(existingGame);
    }

    @Override
    @Transactional
    public void deleteGame(Long gameId, Long userId) {
        Game existingGame = getGameById(id);

        boolean isAdmin = userDetails.isAdmin();
        boolean isAuthor = Objects.equals(existingGame.getAuthor().getId(), userDetails.getId());

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("Access denied.");
        }

        existingGame.getCategories().clear();

        fileService.deleteFileIfExists(existingGame.getImageUrl());
        fileService.deleteFileIfExists(existingGame.getGameFileUrl());

        gameRepository.delete(existingGame);
    }
}
