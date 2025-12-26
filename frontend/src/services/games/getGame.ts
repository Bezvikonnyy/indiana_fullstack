import {GameFullDto} from "../../types/GameFullDto";
import {request} from "../../api/httpClient";

export const getGame = async (id) => {
    return request<GameFullDto>(`/api/game/${id}`);
}
