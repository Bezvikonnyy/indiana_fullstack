import React, {FC} from "react";
import './GameCard.css'
import {GameCardType} from "../constant/GameCardType";
import {FavoriteButton} from "./buttons/FavoriteButton";
import {CartButton} from "./buttons/CartButton";
import {PurchasedStatus} from "./buttons/PurchasedStatus";
import {NavLink} from "react-router-dom";

export interface GameCardProps {
    game: GameCardType;
}

export const GameCard: FC<GameCardProps> = ({game}) => {
    const isLoggedIn = !!localStorage.getItem('token');

    return (
        <div className="gameCard">
            <NavLink to={`/games/${game.id}`} className={"gameCardLink"}>
                <img src={`http://localhost:8080${game.imageUrl}`} alt={game.title} className="gameCardImage"/>
                <p className="gameCardTitle">{game.title}</p>
                <p className="gameCardPrice">{game.price}</p>
            </NavLink>
            {isLoggedIn && (
                <div className="gameCardButton">
                    <FavoriteButton gameId={game.id} isFavorite={game.isFavorite}/>
                    <CartButton gameId={game.id} isInCart={game.isInCart}/>
                    <PurchasedStatus isPurchased={game.isPurchased}/>
                </div>
            )}
        </div>
    )
        ;
};
