package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

public interface UserForControllerService {

    UserDto getProfile(CustomUserDetails userDetails);

    UserDto registerUser(NewUserPayload payload);

    UserDto editProfile(EditUserPayload payload, CustomUserDetails user);

    void deleteUser(CustomUserDetails user);

    Set<GameDto> purchasedGame(User userAuth);

    @Transactional
    List<GameDto> myGame(User userAuth);

    Set<GameDto> favoriteGames(User userAuth);

    @Transactional
    GameDto addFavorite(User userAuth, Long id);

    @Transactional
    void removeFavorite(User userAuth, Long id);
}
