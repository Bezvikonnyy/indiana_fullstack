import React, { useEffect, useState } from 'react';
import './FavoritePage.css';
import {getMyFavoriteGame} from "../../services/users/getMyFavoriteGame";
import {GameCard} from "../../components/GameCard";

export const FavoritePage = () => {
    const [favoriteGames, setFavoriteGames] = useState([]);
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        const fetchPurchasedGame = async () => {
            const res = await getMyFavoriteGame();
            if (!res.success) {
                setLoading(false);
                console.log(res.error.message);
            } else {
                setFavoriteGames(res.data);
                setLoading(false);
            }
        }
        void fetchPurchasedGame();
    }, []);

    if (favoriteGames.length === 0) {
        return (
            <div className="favorite-page">
                <h2>У вас пока нет избранных игр</h2>
            </div>
        );
    }

    if (loading) return <p className="gameList-loading">Загрузка...</p>;

    return (
        <div className="favoriteGamePage">
            <h2 className={"profile-form-h2"}>Избранное</h2>
            <div className="favoriteGameList">
                {favoriteGames.map(game => (
                    <GameCard key={game.id} game={game}/>
                ))}
            </div>
        </div>
    );
}
