package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.users.ProfileDto;
import indiana.indi.indiana.dto.users.UserDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.mapper.users.UserMapper;
import indiana.indi.indiana.mapperInterface.games.CardItemMapper;
import indiana.indi.indiana.mapperInterface.users.ProfileMapperInterface;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserForControllerServiceImpl implements UserForControllerService {

    private final CRUDUserServiceImpl userService;
    private final RegisterUserService registerUserService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final UserMapper mapper;
    private final ProfileMapperInterface profileMapper;
    private final CardItemMapper itemMapper;

    @Override
    public ProfileDto getProfile(Long userId) {
        return profileMapper.toDto(userId);
    }

    @Override
    @Transactional
    public UserDto registerUser(NewUserPayload payload) {
        int id = registerUserService.searchRole(payload.roleId(), payload.inviteCode());
        User user = registerUserService.saveUserRole(id, payload.username(), payload.password());
        registerUserService.requestAuthor(payload.roleId(), user);
        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public ProfileDto editProfile(EditUserPayload payload, Long userId) {
        userService.editUserProfile(
                userId, payload.username(), payload.password());
        return profileMapper.toDto(userId);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    @Override
    public Set<CardItemDto> purchasedGame(Long userId) {
        Set<CardItemDtoInter> dtoInter = gameRepository.findBuyersCardItemById(userId);
        return dtoInter.stream().map(itemMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public List<CardItemDto> myGame(Long userId) {
        List<CardItemDtoInter> dtoInter = gameRepository.findAuthorsCardItemById(userId);
        return dtoInter.stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Set<CardItemDto> favoriteGames(Long userId) {
        Set<CardItemDtoInter> dtoInter = gameRepository.findFavoritesCardItemById(userId);
        return dtoInter.stream().map(itemMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void addFavorite(Long userId, Long gameId) {
        userRepository.addGameFavorite(gameId, userId);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long gameId) {
        userRepository.removeGameFavorite(gameId, userId);
    }
}
