package indiana.indi.indiana.dto.users;

import indiana.indi.indiana.dto.games.CardItemDto;

import java.util.List;
import java.util.Set;

public record UserForAdminPanelDto(
        Long id,
        String username,
        Set<RoleDto> roles,
        List<CardItemDto> game,
        String requestUsers) {
}
