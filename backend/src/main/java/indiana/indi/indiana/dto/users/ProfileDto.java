package indiana.indi.indiana.dto.users;

import java.time.LocalDateTime;

public record ProfileDto(
        Long id,
        String username,
        RoleDto role,
        LocalDateTime createdAt
) {
}
