import {FC} from "react";
import {GameCard} from "../constant/GameCard";

export interface GameCardProps{
    card:GameCard;
}
export const GameCard:FC<GameCardProps> = ({card}) => {
   return (
       <div className="">{card.title}</div>
   );
};
