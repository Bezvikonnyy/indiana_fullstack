package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.entity.users.Role;

import java.util.Set;

public record EditUserPayload(Long id, String username, String password, Role role) {
}
