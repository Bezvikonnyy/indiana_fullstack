package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.RoleDto;
import indiana.indi.indiana.dto.UserForAdminPanelDto;
import indiana.indi.indiana.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserForAdminPanelMapper {

    public UserForAdminPanelDto toDto(User user, String requestUser){
        UserForAdminPanelDto userDto = new UserForAdminPanelDto(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(r -> new RoleDto(r.getId(), r.getTitle())).collect(Collectors.toSet()),
                user.getGames().stream().map(g -> new GameDto(g.getId(), g.getTitle(), g.getImageUrl())).toList(),
                requestUser
        );
        return userDto;
    }

}
