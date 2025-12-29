import {request} from "../../api/httpClient";
import {GameCardType} from "../../constant/GameCardType";

export const getNewGames = async () => {
    return request<GameCardType[]>(`/api/home/newGame`);
}