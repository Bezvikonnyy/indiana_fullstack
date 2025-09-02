package indiana.indi.indiana.service.invite;

import indiana.indi.indiana.entity.users.InviteCode;

public interface CRUDInviteCodeInterfaceService {

    InviteCode createInviteCode();

    InviteCode getInviteCode();

    void deleteInviteCode();

    void deleteInviteCodeByCode(String code);
}
