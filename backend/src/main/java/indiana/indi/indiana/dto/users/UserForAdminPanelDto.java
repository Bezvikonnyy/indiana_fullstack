package indiana.indi.indiana.dto.users;

public record UserForAdminPanelDto(
        Long id,
        String username,
        String role,
        String requestUsers) {
}
