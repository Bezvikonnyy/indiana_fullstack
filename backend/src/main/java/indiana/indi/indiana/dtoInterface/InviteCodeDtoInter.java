package indiana.indi.indiana.dtoInterface;

import java.time.LocalDateTime;

public interface InviteCodeDtoInter {
    Long getId();
    String getCode();
    boolean getUsed();
    LocalDateTime getCreateAt();
    LocalDateTime getExpiresAt();
}
