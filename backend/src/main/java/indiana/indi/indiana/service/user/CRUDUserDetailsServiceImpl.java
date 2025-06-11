package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CRUDUserDetailsServiceImpl implements CRUDUserDetailsService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        return this.userRepository.save(user);
    }

    @Override
    public User editUser(Long id, String username, String password, Set<Role> roles) throws UsernameNotFoundException{
        return this.userRepository.findById(id).map(
                user -> {
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setRoles(roles);

                    return userRepository.save(user);
                }).orElseThrow(() -> new UsernameNotFoundException("User not found!" + id));
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) throws UsernameNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
