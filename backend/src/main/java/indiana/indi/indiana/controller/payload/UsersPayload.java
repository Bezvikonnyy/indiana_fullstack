package indiana.indi.indiana.controller.payload;

import jakarta.validation.constraints.NotBlank;

public record UsersPayload(
        @NotBlank String username, @NotBlank String password) {
}
