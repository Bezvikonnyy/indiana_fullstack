package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.games.GameFullDto;
import indiana.indi.indiana.dtoInterface.games.GameDetailsDtoInter;
import indiana.indi.indiana.entity.categories.Category;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.manyToManyEntities.GameCategory;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.mapper.games.GameFullMapper;
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
    private final GameFullMapper mapper;

    @Override
    @Transactional
    public GameFullDto createGame(
            NewGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            Long userId
    ) {
        String imageFileUrl = fileService.saveFile(imageFile, "imageFile");
        String gameFileUrl = fileService.saveFile(gameFile, "gameFile");

        List<Category> categories = categoryService.validCategoryByGame(payload.categoryId());
        User author = userRepository.getReferenceById(userId);

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
        return mapper.toDto(game, categories);
    }

    @Override
    @Transactional
    public GameFullDto editGame(
            Long gameId,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            Long userId
    ) {
        Game existingGame = getGameById(gameId);
        accessRight(userId, existingGame);
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
        existingGame.setPrice(payload.price());
        gameRepository.save(existingGame);
        gameCategoryRepository.deleteGameCategoriesByGameId(existingGame.getId());
        List<GameCategory> links = categories.stream()
                .map(category -> new GameCategory(null, existingGame, category)).toList();
        gameCategoryRepository.saveAll(links);
        return mapper.toDto(existingGame, categories);
    }

    public GameDetailsDtoInter getGame(Long userId, Long gameId) {
        return gameRepository.getGameDetailsById(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found!"));
    }

    @Override
    @Transactional
    public void deleteGame(Long gameId, Long userId) {
        Game existingGame = getGameById(gameId);
        accessRight(userId, existingGame);

        fileService.deleteFileIfExists(existingGame.getImageUrl());
        fileService.deleteFileIfExists(existingGame.getGameFileUrl());

        gameRepository.delete(existingGame);
    }

    @Override
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game ID " + gameId + " not found."));
    }

    public void accessRight(Long userId, Game game) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        boolean isAdmin = user.getRole().toString().equals("ROLE_ADMIN");
        boolean isAuthor = Objects.equals(game.getAuthor().getId(), userId);
        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("Access denied.");
        }
    }
}
