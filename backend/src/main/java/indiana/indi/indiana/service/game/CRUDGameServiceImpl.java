package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.GameRepository;
import indiana.indi.indiana.service.categories.CategoryService;
import indiana.indi.indiana.service.user.CustomUserDetails;
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
    private final CategoryService categoryService;
    private final FileService fileService;

    @Override
    @Transactional
    public Game createGame(
            NewGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            User author
    ) {
        String imageFileUrl;
        String gameFileUrl;

        imageFileUrl = fileService.saveFile(imageFile, "imageFile");
        gameFileUrl = fileService.saveFile(gameFile, "gameFile");

        List<Category> categories = categoryService.validCategoryByGame(payload.categoryId());

        Game game = Game.builder()
                .title(payload.title())
                .details(payload.details())
                .imageUrl(imageFileUrl)
                .gameFileUrl(gameFileUrl)
                .categories(categories)
                .author(author)
                .build();

        return gameRepository.save(game);
    }

    @Override
    public Game findGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game ID " + gameId + " not found."));
    }

    @Override
    @Transactional
    public Game editGame(
            Long id,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            CustomUserDetails userDetails
    ) {
        Game existingGame = findGame(id);

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

        return gameRepository.save(existingGame);
    }

    @Override
    @Transactional
    public void deleteGame(Long id, CustomUserDetails userDetails) {
        Game existingGame = findGame(id);

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
