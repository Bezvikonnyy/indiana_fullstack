package indiana.indi.indiana.mapperInterface.users;

import indiana.indi.indiana.dto.users.ProfileDto;
import indiana.indi.indiana.dto.users.RoleDto;
import indiana.indi.indiana.dtoInterface.games.GameForProfileDtoInter;
import indiana.indi.indiana.dtoInterface.users.ProfileDtoInter;
import indiana.indi.indiana.mapperInterface.games.GameForProfileMapperInterface;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileMapperInterface {

    private final GameForProfileMapperInterface mapper;

    private final UserRepository userRepository;

    private final GameRepository gameRepository;

    public ProfileDto toDto(Long id) {
        ProfileDtoInter profileInter = userRepository.getProfile(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        List<GameForProfileDtoInter> authorsGame = gameRepository.findAuthorsGameById(id);
        Set<GameForProfileDtoInter> purchasedGame = gameRepository.findBuyersGameById(id);
        Set<GameForProfileDtoInter> favoritesGame = gameRepository.findFavoritesGameById(id);
        return new ProfileDto(
                profileInter.getId(),
                profileInter.getUsername(),
                new RoleDto(profileInter.getRole()),
                profileInter.getCreatedAt(),
                authorsGame.stream().map(g -> mapper.toDto(g)).collect(Collectors.toList()),
                purchasedGame.stream().map(g -> mapper.toDto(g)).collect(Collectors.toSet()),
                favoritesGame.stream().map(g -> mapper.toDto(g)).collect(Collectors.toSet())
        );
    }
}
