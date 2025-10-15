package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.RoleDto;
import indiana.indi.indiana.entity.users.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role){
        return new RoleDto(role.getId(), role.getTitle());
    }
}
