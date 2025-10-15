package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.UserDto;
import indiana.indi.indiana.entity.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper mapper;

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                mapper.toDto(user.getRole())
        );
    }
}
