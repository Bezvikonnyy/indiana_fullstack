package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.service.game.CRUDGameServiceImpl;
import indiana.indi.indiana.service.game.FileService;
import indiana.indi.indiana.service.game.GameService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final CRUDGameServiceImpl crudGameService;

    private final GameService gameService;

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<Iterable<Game>> findAllGames(@RequestParam(name = "filter", required = false) String filter) {
        return ResponseEntity.ok(gameService.findAllGames(filter));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable("gameId") long gameId) {
        Game game = crudGameService.findGame(gameId)
                .orElseThrow(() -> new NoSuchElementException("game.not_found"));
        return ResponseEntity.ok(game);
    }

    @PostMapping("/new_game")
    public ResponseEntity<?> createGame(
            @Valid NewGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            @AuthenticationPrincipal CustomUserDetails currentUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Game game = crudGameService.createGame(payload, imageFile, gameFile, currentUser.getUser());

        return ResponseEntity.ok(game);
    }

    @PostMapping("/edit/{gameId}")
    public ResponseEntity<?> editGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid EditGamePayload payload,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "gameFile", required = false) MultipartFile gameFile,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Game updateGame = crudGameService.editGame(
                gameId,
                payload,
                imageFile,
                gameFile,
                userDetails
        );
        return ResponseEntity.ok(updateGame);
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity<Void> deleteGame(
            @PathVariable("gameId") Long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        crudGameService.deleteGame(gameId, userDetails);
        return ResponseEntity.noContent().build();
    }

}
