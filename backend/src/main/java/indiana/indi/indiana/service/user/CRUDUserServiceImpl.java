package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.dto.users.AdminEditUserDto;
import indiana.indi.indiana.dto.users.EditUserDto;
import indiana.indi.indiana.dtoInterface.users.AdminEditUserDtoInter;
import indiana.indi.indiana.dtoInterface.users.EditUserDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Cart;
import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CRUDUserServiceImpl implements CRUDUserService{

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user."));
    }

    @Transactional
    @Override
    public EditUserDto editUserProfile(Long userId, String username, String password) {
        int editProfile = userRepository.updateUserProfile(userId, username, passwordEncoder.encode(password));
        if (editProfile==0) {
            throw new EntityNotFoundException("User not found.");
        }
        EditUserDtoInter editUser = userRepository.findByIdEditUserDto(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        return new EditUserDto(editUser.getId(), editUser.getUsername(), editUser.getPassword());
    }

    @Transactional
    @Override
    public AdminEditUserDto adminEditUser(EditUserPayload payload) {
        int adminEditUser = userRepository.updateUserByAdmin(
                payload.id(),
                payload.username(),
                passwordEncoder.encode(payload.password()),
                payload.role());
        if (adminEditUser==0) {
            throw new EntityNotFoundException("User not found.");
        }
        AdminEditUserDtoInter adminEditUserDto = userRepository.findByIdAdminEditUserDto(payload.id())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        return new AdminEditUserDto(
                adminEditUserDto.getId(),
                adminEditUserDto.getUsername(),
                adminEditUserDto.getPassword(),
                adminEditUserDto.getRole());
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id: " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
