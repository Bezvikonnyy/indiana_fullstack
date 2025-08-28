package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.RoleDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final GameMapper gameMapper;
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(r -> new RoleDto(r.getId(), r.getTitle())).collect(Collectors.toSet()),
                user.getGames().stream().map(g -> gameMapper.toDto(g, user) )
                        .toList()
        );
    }
}
