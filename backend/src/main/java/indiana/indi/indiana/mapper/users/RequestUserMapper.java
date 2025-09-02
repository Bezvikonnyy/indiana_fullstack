package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.RequestUserDto;
import indiana.indi.indiana.entity.users.RequestUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestUserMapper {

    public RequestUserDto toDto(RequestUsers request){

        return new RequestUserDto(
                request.getId(),
                request.getBodyRequest()
        );
    }
}
