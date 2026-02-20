package indiana.indi.indiana.dtoInterface.users;

import java.time.LocalDateTime;

public interface ProfileDtoInter {
    Long getId();
    String getUsername();
    String getRole();
    LocalDateTime getCreatedAt();
}
