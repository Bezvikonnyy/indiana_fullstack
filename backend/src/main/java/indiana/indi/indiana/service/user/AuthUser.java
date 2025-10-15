package indiana.indi.indiana.service.user;

import indiana.indi.indiana.config.JWTUtil;
import indiana.indi.indiana.controller.payload.AuthUserPayload;
import indiana.indi.indiana.dto.users.AuthDto;
import indiana.indi.indiana.dto.users.AuthResponseDto;
import indiana.indi.indiana.mapperInterface.users.AuthMapperInterface;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUser {

    private final JWTUtil jwtUtil;

    private final AuthMapperInterface authMapper;

    private final AuthenticationManager authenticationManager;

    public AuthResponseDto login(AuthUserPayload payload){
        var authInputToken = new UsernamePasswordAuthenticationToken(payload.username(), payload.password());
        var authentication = authenticationManager.authenticate(authInputToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        AuthDto userDto = authMapper.toDto(userDetails);
        return new AuthResponseDto(token, userDto);
    }
}
