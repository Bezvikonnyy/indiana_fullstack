package indiana.indi.indiana.service.user;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.RoleDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.RoleRepository;
import indiana.indi.indiana.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService{

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

    public UserDto userDtoCreate(User user) {
        UserDto dto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(r -> new RoleDto(r.getId(), r.getTitle())).collect(Collectors.toSet()),
                user.getGames().stream().map(g -> new GameDto(g.getId(), g.getTitle(), g.getImageUrl())).toList()
        );
        return dto;
    }

}
