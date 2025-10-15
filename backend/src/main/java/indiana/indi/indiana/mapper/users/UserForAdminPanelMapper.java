package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.RoleDto;
import indiana.indi.indiana.dto.users.UserForAdminPanelDto;
import indiana.indi.indiana.dtoInterface.users.UserForAdminPanelDtoInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserForAdminPanelMapper {

    public UserForAdminPanelDto toDto(UserForAdminPanelDtoInter dtoInter){
        return new UserForAdminPanelDto(
                dtoInter.getId(),
                dtoInter.getUsername(),
                new RoleDto(dtoInter.getRole().getTitle()),
                dtoInter.getRequestUsers()
        );
    }

}
