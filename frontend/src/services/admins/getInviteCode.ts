import {request} from "../../api/httpClient";

export const getInviteCode = async () => {
    return request<InviteCodeDto[]>(`/api/admin/inviteCode`);
}