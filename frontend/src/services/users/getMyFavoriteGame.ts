import {request} from "../../api/httpClient";
import {GameCardType} from "../../constant/GameCardType";

export const getMyFavoriteGame = async () => {
    return request<GameCardType[]>('/api/user/myFavoriteGames');
}