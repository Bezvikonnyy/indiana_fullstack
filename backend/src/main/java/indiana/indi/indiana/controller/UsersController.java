package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.service.user.CustomUserDetails;
import indiana.indi.indiana.service.user.UserForControllerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {
    private final UserForControllerServiceImpl service;

    @GetMapping("/profile")
    public UserDto getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return service.getProfile(userDetails);
    }

    @PostMapping("/registration")
    public UserDto registerUser(@Valid @RequestBody NewUserPayload payload) {
        return service.registerUser(payload);
    }

    @PutMapping("/edit_profile")
    public UserDto editProfile(@Valid @RequestBody EditUserPayload payload,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return service.editProfile(payload, userDetails);
    }

    @DeleteMapping("/delete_profile")
    public void deleteProfile(@AuthenticationPrincipal CustomUserDetails user) {
        service.deleteUser(user);
    }
}
