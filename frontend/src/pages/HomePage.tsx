import './HomePage.css';
import {NavLink} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import {hasRole} from "../utils/auth";
import {getNewGames} from "../services/homes/getNewGames";
import {GameCard} from "../components/GameCard";
import {getGameDiscount} from "../services/homes/getGameDiscount";

export const HomePage = () => {

    const isLoggedIn = !!localStorage.getItem('token');
    const [menuOpen, setMenuOpen] = useState(false);
    const menuRef = useRef(null);

    const isAdmin = hasRole(['ADMIN']);
    const isAuthor = hasRole(['AUTHOR', 'ADMIN']);

    const [games, setGame] = useState([]);


    useEffect(() => {
        getNewGames(setGame);
    }, [])

    useEffect(() => {
        getGameDiscount(setGame);
    }, [])

    return (
        <div className="homePage">
            <div className="linkContainerHomePage">
                <NavLink to="">Все игры</NavLink>
                <NavLink to="">Новинки</NavLink>
                <NavLink to="">Акции</NavLink>
                <NavLink to="">Популярные</NavLink>
                <NavLink to="">Новости</NavLink>
                {isAuthor && (
                    <NavLink to="/games/create">Добавить игру</NavLink>
                )}
            </div>
            <div className="gameRecommendationHomePage">
                {games.map(game => (
                    <GameCard key={game.id} game={game} />
                ))}
            </div>
            <div className="gameRecommendationHomePage">
                {games.map(game => (
                    <GameCard key={game.id} game={game} />
                ))}
            </div>
        </div>
    );
}
