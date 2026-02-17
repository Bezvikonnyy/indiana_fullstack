import {GameCardType} from "../constant/GameCardType";

export interface CategoryPageDto {
    cardItems: GameCardType[];
    page: number;
    totalPages: number;
    totalElements: number;
}