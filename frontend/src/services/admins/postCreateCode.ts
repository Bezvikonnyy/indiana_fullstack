import {request} from "../../api/httpClient";

export const postCreateCode = async () => {
    return request<InviteCodeDto>(`/api/admin/createInvite`, {
        method: 'POST'
    });
}