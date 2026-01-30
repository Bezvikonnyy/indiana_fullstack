import {request} from "../../api/httpClient";

export const postDeleteUser = async (id: number) => {
    return request<void>(`/api/admin/delete/user/${id}`, {
        method: 'DELETE'
    });
}