import {request} from "../../api/httpClient";
import {GameCardType} from "../../constant/GameCardType";

export const getPurchasedGames = async () => {
    return request<GameCardType[]>('/api/user/purchasedGame');
}