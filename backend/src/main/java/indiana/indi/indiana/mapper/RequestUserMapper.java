package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.RequestUserDto;
import indiana.indi.indiana.entity.RequestUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestUserMapper {

    public RequestUserDto toDto(RequestUsers request){

        RequestUserDto requestUserDto = new RequestUserDto(
                request.getId(),
                request.getBodyRequest()
        );
        return requestUserDto;
    }
}
