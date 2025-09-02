package indiana.indi.indiana.dto;

import java.util.List;
import java.util.Set;

public record UserForAdminPanelDto(
        Long id,
        String username,
        Set<RoleDto> roles,
        List<CardItemDto> game,
        String requestUsers) {
}
