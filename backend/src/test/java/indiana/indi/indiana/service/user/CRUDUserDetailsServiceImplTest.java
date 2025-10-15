package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    CRUDUserServiceImpl serviceImpl;

    @Test
    void createUser() {
        //given
        String username = "sergio";
        String password = "lovevika";
        String passwordCode = "passcod";
        Role role = new Role(1, "USER");
        //when
        when(passwordEncoder.encode(password)).thenReturn(passwordCode);

        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = serviceImpl.createUser(username, password, role);
        //then
        assertEquals(username, result.getUsername());
        assertEquals(passwordCode, result.getPassword());
        assertEquals(role, result.getRole());

        verify(repository).save(any(User.class));
        verify(passwordEncoder).encode(password);
    }
}