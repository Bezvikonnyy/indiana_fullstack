import {request} from "../../api/httpClient";

export const postFavoriteToggle = async (gameId: number) => {
    return request<{isFavorite: boolean}>(`/api/user/favorite/toggle/${gameId}`, {
        method: 'POST'
    })
}
