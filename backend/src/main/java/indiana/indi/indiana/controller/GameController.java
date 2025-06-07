package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.service.categories.CategoryService;
import indiana.indi.indiana.service.game.GameService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    private final CategoryService categoryService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    private String saveFile(MultipartFile file, String folder) throws IOException {
        String folderPath = UPLOAD_DIR + folder;
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String fileUrl = folderPath + "/" + fileName;
            File destination = new File(fileUrl);
            file.transferTo(destination);
            return "/uploads/" + folder + "/" + fileName;
        }
        return null;
    }

    private void deleteFileIfExists(String path) {
        if (path == null) return; // Проверка на null
        File file = new File(System.getProperty("user.dir") + path);
        if (file.exists()) {
            file.delete();
        }
    }

    private boolean isAdmin(CustomUserDetails userDetails) {
        if (userDetails == null) return false;
        return userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("Администратор"));
    }

    @GetMapping("/find_all")
    public Iterable<Game> findAllGames(@RequestParam(name = "filter", required = false) String filter) {
        return this.gameService.findAllGames(filter);
    }

    @GetMapping("/find")
    public Game findGame(@ModelAttribute("game") Game game) {
        return game;
    }

    @GetMapping("/{gameId}")
    public String getGameForm(@PathVariable("gameId") long gameId,
                              Model model,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Game game = this.gameService.findGame(gameId)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));

        model.addAttribute("game", game);
        model.addAttribute("currentUserId", userDetails != null ? userDetails.getId() : null);
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "game";
    }

    @GetMapping("/edit/{gameId}")
    public String editGameForm(@PathVariable("gameId") long gameId,
                               Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Game game = gameService.findGame(gameId).orElseThrow(() -> new NoSuchElementException("Игра не найдена"));

            // Проверка прав доступа на редактирование
            if (userDetails == null ||
                    (game.getAuthor().getId() != userDetails.getId() && !isAdmin(userDetails))) {
                model.addAttribute("error", "У вас нет прав для редактирования этой игры");
                return "error";
            }

            model.addAttribute("game", game);
            model.addAttribute("allCategories", categoryService.findAll());
            return "edit_game";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка при отображении формы: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/new_game")
    public String newGameForm(Model model) {
        try {
            model.addAttribute("game", new Game());
            model.addAttribute("allCategories", categoryService.findAll());
            return "new_game";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка при отображении формы: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/new_game")
    public ResponseEntity<?> createGame(
            @Valid NewGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) throws IOException {

        String imageFileUrl = saveFile(imageFile, "imageFile");
        String gameFileUrl = saveFile(gameFile, "gameFile");

        Game game = this.gameService.createGame(
                payload.title(),
                payload.details(),
                imageFileUrl,
                gameFileUrl,
                payload.categoryId(),
                payload.comments(),
                currentUser.getUser()
        );

        String redirectUrl = "/game/" + game.getId();
        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

    @PostMapping("/edit/{gameId}")
    public ResponseEntity<?> editGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid EditGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Game existingGame = gameService.findGame(gameId)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));

        boolean admin = isAdmin(userDetails);

        if (existingGame.getAuthor().getId() != userDetails.getId() && !admin) {
            return ResponseEntity.status(403).body("Вы не являетесь автором этой игры и не администратор!");
        }

        String imageFileUrl = imageFile != null && !imageFile.isEmpty()
                ? saveFile(imageFile, "imageFile")
                : existingGame.getImageUrl();

        String gameFileUrl = gameFile != null && !gameFile.isEmpty()
                ? saveFile(gameFile, "gameFile")
                : existingGame.getGameFileUrl();

        this.gameService.editGame(
                gameId,
                payload.title(),
                payload.details(),
                imageFileUrl,
                gameFileUrl,
                payload.categoryId()
        );
        String redirectUrl = "/game/" + gameId;
        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity<Void> deleteGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Game existingGame = gameService.findGame(gameId)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));

        boolean admin = isAdmin(userDetails);

        if (existingGame.getAuthor().getId() != userDetails.getId() && !admin) {
            return ResponseEntity.status(403).build();
        }

        Game game = gameService.findGame(gameId).orElseThrow();

        deleteFileIfExists(game.getImageUrl());
        deleteFileIfExists(game.getGameFileUrl());

        game.getCategories().clear();
        this.gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }

}
