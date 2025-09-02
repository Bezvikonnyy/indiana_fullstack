package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.CardItemDto;
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

    Set<CardItemDto> purchasedGame(User userAuth);

    @Transactional
    List<CardItemDto> myGame(User userAuth);

    Set<CardItemDto> favoriteGames(User userAuth);

    @Transactional
    CardItemDto addFavorite(User userAuth, Long id);

    @Transactional
    void removeFavorite(User userAuth, Long id);
}
