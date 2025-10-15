package indiana.indi.indiana.dto.users;

public record UserForAdminPanelDto(
        Long id,
        String username,
        RoleDto role,
        String requestUsers) {
}
