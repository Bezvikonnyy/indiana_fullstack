package indiana.indi.indiana.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUserPayload(
        @NotBlank @Size(min = 3, max = 32)
        String username,

        @NotBlank @Size(min = 6)
        String password,

        @NotNull
        int roleId,

        String inviteCode
) {}
