import {request} from "../../api/httpClient";

export const postDeleteCode = async (inviteCodeId: number) => {
    return request<InviteCodeDto>(`/api/admin/delete/invite/${inviteCodeId}`, {
        method: 'DELETE'
    });
}