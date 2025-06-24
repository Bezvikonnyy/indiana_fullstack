package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.AuthUserPayload;
import indiana.indi.indiana.dto.AuthResponseDto;
import indiana.indi.indiana.service.user.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthUserController {

    private final AuthUser authUser;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthUserPayload payload) {
        return authUser.login(payload);
    }
}
