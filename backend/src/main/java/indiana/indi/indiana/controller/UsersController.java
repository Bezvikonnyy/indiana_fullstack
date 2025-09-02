package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.users.UserDto;
import indiana.indi.indiana.service.user.CustomUserDetails;
import indiana.indi.indiana.service.user.UserForControllerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {
    private final UserForControllerServiceImpl service;

    @GetMapping("/profile")
    public UserDto getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return service.getProfile(userDetails);
    }

    @PostMapping("/registration")
    public UserDto registerUser(@Valid @RequestBody NewUserPayload payload) {
        return service.registerUser(payload);
    }

    @PutMapping("/edit_profile")
    public UserDto editProfile(@Valid @RequestBody EditUserPayload payload,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return service.editProfile(payload, userDetails);
    }

    @DeleteMapping("/delete_profile")
    public void deleteProfile(@AuthenticationPrincipal CustomUserDetails user) {
        service.deleteUser(user);
    }

    @GetMapping("/purchased_game")
    public Set<CardItemDto> getPurchasedGame(@AuthenticationPrincipal CustomUserDetails user) {
        return service.purchasedGame(user.getUser());
    }

    @GetMapping("/my_game")
    public List<CardItemDto> getMyGame(@AuthenticationPrincipal CustomUserDetails user) {
        return service.myGame(user.getUser());
    }

    @GetMapping("/my_favorite_games")
    public Set<CardItemDto> getMyFavoriteGames(@AuthenticationPrincipal CustomUserDetails user) {
        return service.favoriteGames(user.getUser());
    }

    @PostMapping("/add_favorite/{gameId}")
    public CardItemDto addFavoriteGame(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long gameId) {
        return service.addFavorite(user.getUser(), gameId);
    }

    @DeleteMapping("/remove_favorite/{gameId}")
    public void removeFavoriteGame(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long gameId) {
        service.removeFavorite(user.getUser(), gameId);
    }
}
