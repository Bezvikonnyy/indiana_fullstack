package indiana.indi.indiana.dto.users;

import indiana.indi.indiana.dto.games.CardItemDto;

import java.util.List;
import java.util.Set;

public record UserDto(Long id, String username, Set<RoleDto> roles, List<CardItemDto> games) {
}
