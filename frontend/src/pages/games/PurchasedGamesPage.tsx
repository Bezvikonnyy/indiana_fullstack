import './PurchasedGamePage.css';
import React, {useEffect, useState} from 'react';
import {getPurchasedGames} from "../../services/users/getPurchasedGames";
import {GameCard} from "../../components/GameCard";

export const PurchasedGamesPage = () => {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchPurchasedGame = async () => {
            const res = await getPurchasedGames();
            if (!res.success) {
                setLoading(false);
                console.log(res.error.message);
            } else {
                setGames(res.data);
                setLoading(false);
            }
        }
        void fetchPurchasedGame();
    }, []);

    if (loading) return <p className="gameList-loading">Загрузка...</p>;

    return (
        <div className="purchasedGamePage">
            <h2 className={"profile-form-h2"}>Мои покупки</h2>
            <div className="purchasedGameList">
                {games.map(game => (
                    <GameCard key={game.id} game={game}/>
                ))}
            </div>
        </div>
    );
}
