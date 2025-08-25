package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.UserMapper;
import indiana.indi.indiana.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserForControllerServiceImpl implements UserForControllerService {

    private final CRUDUserDetailsServiceImpl service;

    private final RegisterUserService registerUserService;

    private final UserMapper mapper;

    private final UserRepository userRepository;

    @Override
    public UserDto getProfile(CustomUserDetails userDetails) {
        return mapper.toDto(service.getUser(userDetails.getId()));
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
        User editUser = service.editUser(
                user.getId(), payload.username(), payload.password(), userDetails.getUser().getRoles());
        return mapper.toDto(editUser);
    }

    @Override
    public void deleteUser(CustomUserDetails user) {
        service.deleteUser(user.getId());
    }

    public Set<GameDto> purchasedGame(User userAuth) {
        User user = userRepository.findById(userAuth.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        Set<Game> games = user.getPurchasedGames();
        return games.stream()
                .map(game -> new GameDto(game.getId(), game.getTitle(), game.getImageUrl(), game.getPrice()))
                .collect(Collectors.toSet());
    }
}
