import { request } from "../../api/httpClient";
import {GamesPageDto} from "../../types/games/GamesPageDto";

export const getDiscountGames = async (page: number = 0, size: number = 20) => {
    return request<GamesPageDto>(`/api/game/discount?page=${page}&size=${size}`);
};
