import {request} from "../../api/httpClient";

export const postDeleteRequest = async (id: number) => {
    return request<UserForAdminPanelDto>(`/api/admin/delete/request/${id}`, {
        method: 'DELETE'
    });
}