package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;

import java.util.Set;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    User createUser(String username, String password, Set<Role> roles);
    User editUser(Long id, String username, String password, Set<Role> roles);
    User getUserByUsername(String username);
    void deleteUser(Long id);
}
