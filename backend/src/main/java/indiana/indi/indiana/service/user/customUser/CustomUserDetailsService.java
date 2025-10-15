package indiana.indi.indiana.service.user.customUser;

import indiana.indi.indiana.dtoInterface.users.AuthDtoInter;
import indiana.indi.indiana.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDtoInter authUser = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found."));
        return new CustomUserDetails(authUser);
    }
}
