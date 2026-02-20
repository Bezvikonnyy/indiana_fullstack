import {GameCardType} from "../../constant/GameCardType";

export type CategoryWithGames = {
    id: number
    title: string
    games: GameCardType[]
}