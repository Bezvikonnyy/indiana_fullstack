package indiana.indi.indiana.dto.users;

import indiana.indi.indiana.dto.games.GameForProfileDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record ProfileDto(
        Long id,
        String username,
        RoleDto role,
        LocalDateTime createdAt,
        List<GameForProfileDto> authorsGame,
        Set<GameForProfileDto> favoriteGame,
        Set<GameForProfileDto> purchasedGame) {
}
