package indiana.indi.indiana.dto;

import java.time.LocalDateTime;

public record InviteCodeDto(Long id, String code, boolean used, LocalDateTime createAt, LocalDateTime expiresAt) {
}
