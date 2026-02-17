import {GameCardType} from "../constant/GameCardType";

export interface CategoryWithGames{
    id: number
    title: string
    games: GameCardType[]
}