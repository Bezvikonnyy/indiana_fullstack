package indiana.indi.indiana.dto;

import java.util.List;
import java.util.Set;

public record UserDto(Long id, String username, Set<RoleDto> roles, List<GameDto> games) {
}
