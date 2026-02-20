package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.dto.games.GamesPageDto;
import indiana.indi.indiana.service.game.GameForControllerService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {
    private final GameForControllerService service;

    @GetMapping("/{gameId}")
    public GameDetailsDto getGame(@PathVariable("gameId") Long gameId,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getGame(userId, gameId);
    }

    @PostMapping("/new_game")
    public GameDetailsDto createGame(
            @Valid NewGamePayload payload,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("gameFile") MultipartFile gameFile,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        return service.createGame(payload, imageFile, gameFile, userId);
    }

    @PostMapping("/edit/{gameId}")
    public GameDetailsDto editGame(
            @PathVariable("gameId") long gameId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid EditGamePayload payload,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "gameFile", required = false) MultipartFile gameFile) {
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

    @GetMapping("/latest")
    public GamesPageDto getLatestGames(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size,
                                       @AuthenticationPrincipal CustomUserDetails userDetails
                                            ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getLatestGames(page, size, userId);
    }

    @GetMapping("/discount")
    public GamesPageDto getDiscountGames(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size,
                                            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getDiscountGames(page, size, userId);
    }

    @GetMapping("/popular")
    public GamesPageDto getPopularGames(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size,
                                            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.getPopularsGames(page, size, userId);
    }

    @PostMapping("/rating")
    public GameDetailsDto postGameRating(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestParam("gameId") Long gameId,
                                         @RequestParam("ratingValue") int ratingValue
                                         ){
        Long userId = userDetails != null ? userDetails.getId() : null;
        return service.gameRating(userId, gameId, ratingValue);
    }
}
