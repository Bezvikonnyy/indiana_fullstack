import {CardItemDto} from "./CardItemDto";

export type GamesPageDto = {
    content: CardItemDto[];
    page: number;
    totalPages: number;
    totalElements: number;
}