package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.controller.payload.NewUserPayload;
import indiana.indi.indiana.dto.UserDto;

public interface UserForControllerService {

    UserDto getProfile(CustomUserDetails userDetails);

    UserDto registerUser(NewUserPayload payload);

    UserDto editProfile(EditUserPayload payload, CustomUserDetails user);

    void deleteUser(CustomUserDetails user);
}
