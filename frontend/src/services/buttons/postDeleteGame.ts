import {request} from "../../api/httpClient";

export const postDeleteGame = async (id: string) => {
    return request<void>(`/api/game/delete/${id}`, {
        method: 'DELETE',
    });
}