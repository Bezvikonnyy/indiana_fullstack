import './HomePage.css';
import {NavLink} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import {hasRole} from "../utils/auth";
import {getNewGames} from "../services/homes/getNewGames";
import {GameCard} from "../components/GameCard";
import {getGameDiscount} from "../services/homes/getGameDiscount";
import {getNewNews} from "../services/homes/getNewNews";
import {ShowcaseSection} from "../components/showCase/ShowcaseSection";
import {NewsShowCaseSection} from "../components/showCase/NewsShowCaseSection";

export const HomePage = () => {

    const isLoggedIn = !!localStorage.getItem('token');
    const [menuOpen, setMenuOpen] = useState(false);
    const menuRef = useRef(null);

    const isAdmin = hasRole(['ADMIN']);
    const isAuthor = hasRole(['AUTHOR', 'ADMIN']);

    const [newGames, setNewGame] = useState([]);
    const [discountGames, setDiscountGame] = useState([]);
    const [news, setNews] = useState([]);


    useEffect(() => {
        const fetchNewGames = async () => {
            const res = await getNewGames();
            if (!res.success) {
                console.log(res.error.message)
            } else {
                setNewGame(res.data)
            }
        }
        void fetchNewGames();
    }, [])

    useEffect(() => {
        const fetchGameDiscount = async () => {
            const res = await getGameDiscount();
            if (!res.success) {
                console.log(res.error.message);
            } else {
                setDiscountGame(res.data)
            }
        }
        void fetchGameDiscount();
    }, [])

    useEffect(() => {
        const fetchNewNews = async () => {
            const res = await getNewNews();
            if (!res.success) {
                console.log(res.error.message);
            } else {
                setNews(res.data)
            }
        }
        void fetchNewNews();
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
                {isAuthor && (
                    <NavLink to="/news/create">Добавить новость</NavLink>
                )}
            </div>
            <div className="gameShowCaseHomePage">
                {
                    <ShowcaseSection items={newGames} />
                }
            </div>
            <div className="gameRecommendationHomePage">
                {discountGames.map(game => (
                    <GameCard key={game.id} game={game}/>
                ))}
            </div>
            <div className="gameNewsShowCaseHomePage">
                {
                    <NewsShowCaseSection items={news} />
                }
            </div>
        </div>
    );
}
