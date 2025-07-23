package indiana.indi.indiana.service.user;

import indiana.indi.indiana.config.JWTUtil;
import indiana.indi.indiana.controller.payload.AuthUserPayload;
import indiana.indi.indiana.dto.AuthResponseDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUser {

    private final JWTUtil jwtUtil;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    public AuthResponseDto login(AuthUserPayload payload){
        var authInputToken = new UsernamePasswordAuthenticationToken(payload.username(), payload.password());
        var authentication = authenticationManager.authenticate(authInputToken);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(customUserDetails);
        User user = customUserDetails.getUser();
        UserDto userDto = userMapper.toDto(user);
        return new AuthResponseDto(token, userDto);
    }
}
