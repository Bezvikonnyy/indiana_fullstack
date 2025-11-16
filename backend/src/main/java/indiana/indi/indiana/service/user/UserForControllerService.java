package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.users.ProfileDto;
import indiana.indi.indiana.dto.users.UserDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

public interface UserForControllerService {

    ProfileDto getProfile(Long userId);

    @Transactional
    UserDto registerUser(NewUserPayload payload);

    @Transactional
    ProfileDto editProfile(EditUserPayload payload, Long userId);

    @Transactional
    void deleteUser(Long userId);

    Set<CardItemDto> purchasedGame(Long userId);

    List<CardItemDto> myGame(Long userId);

    Set<CardItemDto> favoriteGames(Long userId);

    @Transactional
    CardItemDto toggleFavorite(Long userId, Long gameId);
}
