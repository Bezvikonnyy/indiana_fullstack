package indiana.indi.indiana.dtoInterface.users;

import indiana.indi.indiana.dto.users.RoleDto;

import java.time.LocalDateTime;

public interface ProfileDtoInter {
    Long getId();
    String getUsername();
    RoleDto getRole();
    LocalDateTime getCreatedAt();
}
