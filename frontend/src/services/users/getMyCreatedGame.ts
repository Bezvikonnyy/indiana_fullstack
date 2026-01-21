import {request} from "../../api/httpClient";
import {GameCardType} from "../../constant/GameCardType";

export const getMyCreatedGame = async () => {
    return request<GameCardType[]>('/api/user/myGame');
}