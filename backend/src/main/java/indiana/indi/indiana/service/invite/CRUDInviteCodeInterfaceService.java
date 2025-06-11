package indiana.indi.indiana.service.invite;

import indiana.indi.indiana.entity.InviteCode;

import java.util.Optional;

public interface CRUDInviteCodeInterfaceService {

    InviteCode createInviteCode();

    Optional<InviteCode> getInviteCode();

    void deleteInviteCode();

    void deleteInviteCodeByCode(String code);
}
