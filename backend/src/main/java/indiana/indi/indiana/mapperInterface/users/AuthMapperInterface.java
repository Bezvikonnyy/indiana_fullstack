package indiana.indi.indiana.mapperInterface.users;

import indiana.indi.indiana.dto.users.AuthDto;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthMapperInterface {

    public AuthDto toDto(CustomUserDetails userDetails) {
        return new AuthDto(userDetails.getId(), userDetails.getUsername(), userDetails.getRole());
    }
}
