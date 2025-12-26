import {request} from "../../api/httpClient";

export const createGame = async (formData) => {
    return request<void>(`/api/game/new_game`, {
        method: 'POST',
        body: formData,
    });
}