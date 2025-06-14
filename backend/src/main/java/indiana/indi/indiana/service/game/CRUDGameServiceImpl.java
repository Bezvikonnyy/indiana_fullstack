package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.GameRepository;
import indiana.indi.indiana.service.categories.CategoryService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

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
        try {
            String imageFileUrl = fileService.saveFile(imageFile, "imageFile");
            String gameFileUrl = fileService.saveFile(gameFile, "gameFile");

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
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файлов", e);
        }
    }



    @Override
    public Optional<Game> findGame(Long gameId) { return this.gameRepository.findById(gameId);}

    @Override
    @Transactional
    public Game editGame(
            Long id,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            CustomUserDetails userDetails
    ) throws IOException {
        List<Category> categories = categoryService.validCategoryByGame(payload.categoryId());

        Game existingGame = findGame(id)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));

        boolean admin = userDetails.isAdmin();

        if (!Objects.equals(existingGame.getAuthor().getId(), userDetails.getId()) && !admin) {
            throw new AccessDeniedException("Вы не являетесь автором этой игры и не администратор!");
        }

        String imageFileUrl = existingGame.getImageUrl();
        String gameFileUrl = existingGame.getGameFileUrl();

        if (imageFile != null && !imageFile.isEmpty()) {
            fileService.deleteFileIfExists(imageFileUrl); // Удаляем старый файл
            imageFileUrl = fileService.saveFile(imageFile, "imageFile");
        }

        if (gameFile != null && !gameFile.isEmpty()) {
            fileService.deleteFileIfExists(gameFileUrl); // Удаляем старый файл
            gameFileUrl = fileService.saveFile(gameFile, "gameFile");
        }

        existingGame.setTitle(payload.title());
        existingGame.setDetails(payload.details());
        existingGame.setImageUrl(imageFileUrl);
        existingGame.setGameFileUrl(gameFileUrl);
        existingGame.setCategories(categories);

        return gameRepository.save(existingGame);
    }

    @Override
    @Transactional
    public void deleteGame(Long id, CustomUserDetails userDetails) {
        Game existingGame = findGame(id)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));

        boolean admin = userDetails.isAdmin();

        if (!Objects.equals(existingGame.getAuthor().getId(), userDetails.getId()) && !admin) {
            throw new AccessDeniedException("Вы не являетесь автором этой игры и не администратор!");
        }

        existingGame.getCategories().clear();
        fileService.deleteFileIfExists(existingGame.getImageUrl());
        fileService.deleteFileIfExists(existingGame.getGameFileUrl());
        gameRepository.delete(existingGame);
    }

}
