package indiana.indi.indiana.service.user;

import indiana.indi.indiana.controller.payload.EditUserPayload;
import indiana.indi.indiana.dto.users.AdminEditUserDto;
import indiana.indi.indiana.dto.users.EditUserDto;
import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;

public interface CRUDUserService{

    User createUser(String username, String password, Role role);

    User getUser(Long userId);

    EditUserDto editUserProfile(Long userId, String username, String password);

    AdminEditUserDto adminEditUser(EditUserPayload payload);

    void deleteUser(Long userId);
}
