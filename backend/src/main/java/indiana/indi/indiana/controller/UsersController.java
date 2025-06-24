package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditUsersPayload;
import indiana.indi.indiana.controller.payload.NewUsersPayload;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.UserMapper;
import indiana.indi.indiana.service.user.CRUDUserDetailsServiceImpl;
import indiana.indi.indiana.service.user.CustomUserDetails;
import indiana.indi.indiana.service.user.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {

    private final CRUDUserDetailsServiceImpl userDetailsService;

    private final UserMapper userMapper;

    private final RegisterUserService registerUserService;

    @GetMapping("/profile")
    public UserDto getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userMapper.toDto(userDetails.getUser());
    }

    @PostMapping("/registration")
    public UserDto registerUser (@Valid @RequestBody NewUsersPayload payload) {
        int id = registerUserService.searchRole(payload.roleId(), payload.inviteCode());
        User user = registerUserService.saveUserRole(id, payload.username(), payload.password());
        registerUserService.requestAuthor(payload.roleId(), user);
        return userMapper.toDto(user);
    }

    @PutMapping("/edit_profile")
    public UserDto editProfile(@Valid @RequestBody EditUsersPayload payload,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        User editUser = userDetailsService.editUser(
                user.getId(), payload.username(), payload.password(), userDetails.getUser().getRoles());
        return userMapper.toDto(editUser);
    }

    @DeleteMapping("/delete_profile")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getId();
        userDetailsService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
