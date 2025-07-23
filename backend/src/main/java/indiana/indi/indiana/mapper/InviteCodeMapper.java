package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.InviteCodeDto;
import indiana.indi.indiana.entity.InviteCode;
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
