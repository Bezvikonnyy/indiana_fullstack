import './CreatedGamePage.css';
import React, {useEffect, useState} from 'react';
import {getMyCreatedGame} from "../../services/users/getMyCreatedGame";
import {GameCard} from "../../components/GameCard";

export const CreatedGamesPage = () => {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMyGame = async () => {
            const res = await getMyCreatedGame();
            if (!res.success) {
                setLoading(false);
                console.log(res.error.message);
            } else {
                setGames(res.data);
                setLoading(false);
            }
        }
        void fetchMyGame();
    }, []);

    if (loading) return <p className="gameList-loading">Загрузка...</p>;

    return (
        <div className="createdGamePage">
            <h2 className={"profile-form-h2"}>Мои игры</h2>
            <div className="createdGameList">
                {games.map(game => (
                    <GameCard key={game.id} game={game} />
                ))}
            </div>
        </div>
    );
}
