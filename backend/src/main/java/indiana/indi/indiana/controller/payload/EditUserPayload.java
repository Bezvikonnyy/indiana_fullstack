package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.entity.Role;

import java.util.Set;

public record EditUserPayload(int id, String username, String password, Set<Role> roles) {
}
