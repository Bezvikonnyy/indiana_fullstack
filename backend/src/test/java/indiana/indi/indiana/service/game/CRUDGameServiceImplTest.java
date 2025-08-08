package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.Category;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.GameRepository;
import indiana.indi.indiana.service.categories.CategoryServiceImpl;
import indiana.indi.indiana.service.user.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CRUDGameServiceImplTest {

    @Mock
    GameRepository repository;

    @Mock
    CategoryServiceImpl categoryService;

    @Mock
    FileService fileService;

    @InjectMocks
    CRUDGameServiceImpl gameService;

    @Test
    void createGame() {
        //arrange
        String title = "gameName";
        String details = "gameDetails";
        BigDecimal price = new BigDecimal(1);
        String imageUrl = "url/to/image.png";
        String gameUrl = "url/to/game.zip";

        MultipartFile imageFile = mock(MultipartFile.class);
        MultipartFile gameFile = mock(MultipartFile.class);

        List<Category> categories = List.of(new Category(1L, "Шутеры", List.of()));

        NewGamePayload payload = new NewGamePayload(title, details, List.of(1L), price);
        User author = new User();
        //act
        when(fileService.saveFile(imageFile, "imageFile")).thenReturn(imageUrl);
        when(fileService.saveFile(gameFile, "gameFile")).thenReturn(gameUrl);
        when(categoryService.validCategoryByGame(payload.categoryId())).thenReturn(categories);
        when(repository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.createGame(payload, imageFile, gameFile, author);
        //assert
        assertEquals(title, result.getTitle());
        assertEquals(details, result.getDetails());
        assertEquals(imageUrl, result.getImageUrl());
        assertEquals(gameUrl, result.getGameFileUrl());
        assertEquals(author, result.getAuthor());

        verify(fileService).saveFile(imageFile, "imageFile");
        verify(fileService).saveFile(gameFile, "gameFile");
        verify(categoryService).validCategoryByGame(payload.categoryId());
        verify(repository).save(any(Game.class));
    }

    @Test
    void findGame() {
        //arrange
        Long id = 1L;
        Game mockGame = new Game();
        mockGame.setId(id);
        mockGame.setTitle("gameName");

        when(repository.findById(id)).thenReturn(Optional.of(mockGame));
        //act
        Game result = gameService.findGame(id);
        //assert
        assertNotNull(result);
        assertEquals("gameName", result.getTitle());
        verify(repository).findById(id);
    }

    @Test
    void deleteGame_byAuthor_shouldDeleteGameAndFiles() {
        // arrange
        Long id = 1L;
        String imageUrl = "/uploads/imageFile/image.png";
        String gameUrl = "/uploads/gameFile/game.zip";

        // создаём игру
        Game mockGame = new Game();
        mockGame.setId(id);
        mockGame.setImageUrl(imageUrl);
        mockGame.setGameFileUrl(gameUrl);
        mockGame.setCategories(new ArrayList<>());

        // создаём пользователя-автора
        User author = new User();
        author.setId(100L);
        mockGame.setAuthor(author);

        // подставляем пользователя, как будто он автор
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(100L);  // тот же ID, что и у автора
        when(userDetails.isAdmin()).thenReturn(false);

        when(repository.findById(id)).thenReturn(Optional.of(mockGame));

        // act
        gameService.deleteGame(id, userDetails);

        // assert
        verify(fileService).deleteFileIfExists(imageUrl);
        verify(fileService).deleteFileIfExists(gameUrl);
        verify(repository).delete(mockGame);
    }

}