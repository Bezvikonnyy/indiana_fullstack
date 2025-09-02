package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.InviteCodeDto;
import indiana.indi.indiana.entity.users.InviteCode;
import org.springframework.stereotype.Component;

@Component
public class InviteCodeMapper {

    public InviteCodeDto toDto(InviteCode inviteCode){
        return new InviteCodeDto(
                inviteCode.getId(),
                inviteCode.getCode(),
                inviteCode.isUsed(),
                inviteCode.getCreatedAt(),
                inviteCode.getExpiresAt()
        );
    }
}
