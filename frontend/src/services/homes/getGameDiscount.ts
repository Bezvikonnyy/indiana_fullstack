import {request} from "../../api/httpClient";
import {GameCardType} from "../../constant/GameCardType";

export const getGameDiscount = async () => {
    return request<GameCardType[]>(`/api/home/gameDiscounts`)
}