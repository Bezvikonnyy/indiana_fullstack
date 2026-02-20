import { request } from "../../api/httpClient";
import { GameDetailsDto } from "../../types/games/GameDetailsDto";

export const postGameRating = async (gameId: number, ratingValue: number) => {
    return request<GameDetailsDto>(`/api/game/rating?gameId=${gameId}&ratingValue=${ratingValue}`, {
        method: 'POST'
    });
}
