package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CRUDUserDetailsServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    CRUDUserDetailsServiceImpl serviceImpl;

    @Test
    void createUser() {
        //given
        String username = "sergio";
        String password = "lovevika";
        String passwordCode = "passcod";
        Set<Role> roles = Set.of(new Role(1, "USER"));
        //when
        when(passwordEncoder.encode(password)).thenReturn(passwordCode);

        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = serviceImpl.createUser(username, password, roles);
        //then
        assertEquals(username, result.getUsername());
        assertEquals(passwordCode, result.getPassword());
        assertEquals(roles, result.getRoles());

        verify(repository).save(any(User.class));
        verify(passwordEncoder).encode(password);
    }
}