package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.GameFullDto;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.service.game.CRUDGameServiceImpl;
import indiana.indi.indiana.service.game.GameService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final CRUDGameServiceImpl crudGameService;

    private final GameService gameService;

    @GetMapping
    public Iterable<Game> findAllGames(@RequestParam(name = "filter", required = false) String filter) {
        return gameService.findAllGames(filter);
    }

    @GetMapping("/{gameId}")
    public GameFullDto getGame(@PathVariable("gameId") long gameId) {
        return crudGameService.getFullDtoGame(gameId);
    }

    @PostMapping("/new_game")
    public Game createGame(
            @Valid NewGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        return crudGameService.createGame(payload, imageFile, gameFile, currentUser.getUser());
    }

    @PostMapping("/edit/{gameId}")
    public Game editGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid EditGamePayload payload,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "gameFile", required = false) MultipartFile gameFile){
        return crudGameService.editGame(
                gameId,
                payload,
                imageFile,
                gameFile,
                userDetails
        );
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
