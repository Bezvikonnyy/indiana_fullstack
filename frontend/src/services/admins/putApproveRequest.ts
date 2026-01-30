import {request} from "../../api/httpClient";

export const putApproveRequest = async (id: number) => {
    return request<UserForAdminPanelDto>(`/api/admin/approve/${id}`, {
        method: 'PUT'
    });
}