package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.dto.games.GameFullDto;
import indiana.indi.indiana.service.game.GameForControllerService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {
    private final GameForControllerService service;

//    @GetMapping
//    public List<GameFullDto> findAllGames(@RequestParam(name = "filter", required = false) String filter) {
//        return service.getAllGames(filter);
//    }

    @GetMapping("/{gameId}")
    public GameDetailsDto getGame(@PathVariable("gameId") Long gameId,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getGame(userId, gameId);
    }

    @PostMapping("/new_game")
    public GameFullDto createGame(
            @Valid NewGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        return service.createGame(payload, imageFile, gameFile, userId);
    }

    @PostMapping("/edit/{gameId}")
    public GameFullDto editGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid EditGamePayload payload,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "gameFile", required = false) MultipartFile gameFile){
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.editGame(
                gameId,
                payload,
                imageFile,
                gameFile,
                userId
        );
    }

    @DeleteMapping("/delete/{gameId}")
    public void deleteGame(
            @PathVariable("gameId") Long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        service.deleteGame(gameId, userId);
    }
}
