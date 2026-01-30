import {request} from "../../api/httpClient";

export const getUsers = async () => {
    return request<PageResponse<UserForAdminPanelDto>>(`/api/admin/users`);
}