import {request} from "../../api/httpClient";
import {GameDetailsDto} from "../../types/games/GameDetailsDto";

export const getGame = async (id) => {
    return request<GameDetailsDto>(`/api/game/${id}`);
}
