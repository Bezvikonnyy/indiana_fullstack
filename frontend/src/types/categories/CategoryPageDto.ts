import {GameCardType} from "../../constant/GameCardType";

export type CategoryPageDto = {
    cardItems: GameCardType[];
    page: number;
    totalPages: number;
    totalElements: number;
}