package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.GameMapper;
import indiana.indi.indiana.mapper.UserMapper;
import indiana.indi.indiana.repository.UserRepository;
import indiana.indi.indiana.service.game.CRUDGameServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserForControllerServiceImpl implements UserForControllerService {

    private final CRUDUserDetailsServiceImpl userService;

    private final CRUDGameServiceImpl gameService;

    private final RegisterUserService registerUserService;

    private final UserMapper mapper;

    private final GameMapper gameMapper;

    private final UserRepository userRepository;

    @Override
    public UserDto getProfile(CustomUserDetails userDetails) {
        return mapper.toDto(userService.getUser(userDetails.getId()));
    }

    @Override
    public UserDto registerUser(NewUserPayload payload) {
        int id = registerUserService.searchRole(payload.roleId(), payload.inviteCode());
        User user = registerUserService.saveUserRole(id, payload.username(), payload.password());
        registerUserService.requestAuthor(payload.roleId(), user);
        return mapper.toDto(user);
    }

    @Override
    public UserDto editProfile(EditUserPayload payload, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        User editUser = userService.editUser(
                user.getId(), payload.username(), payload.password(), userDetails.getUser().getRoles());
        return mapper.toDto(editUser);
    }

    @Override
    public void deleteUser(CustomUserDetails user) {
        userService.deleteUser(user.getId());
    }

    @Override
    public Set<GameDto> purchasedGame(User userAuth) {
        User user = userService.getUserById(userAuth.getId());
        return user.getPurchasedGames().stream().map(g -> gameMapper.toDto(g, user)).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public List<GameDto> myGame(User userAuth) {
        User user = userService.getUserById(userAuth.getId());
        return user.getGames().stream().map(game -> gameMapper.toDto(game, user)).collect(Collectors.toList());
    }

    @Override
    public Set<GameDto> favoriteGames(User userAuth) {
        User user = userService.getUserById(userAuth.getId());
        return user.getFavoriteGames().stream().map(g -> gameMapper.toDto(g, user)).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public GameDto addFavorite(User userAuth, Long id) {
        User user = userService.getUserById(userAuth.getId());
        Game game = gameService.getGameById(id);
        user.getFavoriteGames().add(game);
        userRepository.save(user);
        return gameMapper.toDto(game, user);
    }

    @Override
    @Transactional
    public void removeFavorite(User userAuth, Long id) {
        User user = userService.getUserById(userAuth.getId());
        Game game = gameService.getGameById(id);
        user.getFavoriteGames().remove(game);
        userRepository.save(user);
    }
}
