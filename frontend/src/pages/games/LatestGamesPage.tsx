import React, {useEffect, useState} from "react";
import {getLatestGames} from "../../services/games/getLatestGames";
import {GameCard} from "../../components/GameCard";
import {GamesPageDto} from "../../types/games/GamesPageDto";
import "./LatestGamesPage.css";

export const LatestGamesPage: React.FC = () => {

    const [gamesPage, setGamesPage] = useState<GamesPageDto | null>(null);
    const [page, setPage] = useState(0);

    useEffect(() => {
        const fetchGames = async () => {
            const res = await getLatestGames(page, 20);

            if (!res.success) {
                alert(res.error.message);
            } else {
                setGamesPage(res.data);
            }
        };

        void fetchGames();
    }, [page]);

    if (!gamesPage) return <p>Загрузка...</p>;

    return (
        <div className="latest-games-container">

            <h1 className="latest-games-title">Новинки</h1>

            <div className="latest-games-grid">
                {gamesPage.content.map(game => (
                    <GameCard key={game.id} game={game}/>
                ))}
            </div>

            <div className="pagination">
                <button
                    disabled={page === 0}
                    onClick={() => setPage(prev => prev - 1)}
                >
                    Назад
                </button>

                <span>
                    Страница {gamesPage.page + 1} из {gamesPage.totalPages}
                </span>

                <button
                    disabled={page + 1 >= gamesPage.totalPages}
                    onClick={() => setPage(prev => prev + 1)}
                >
                    Вперед
                </button>
            </div>

        </div>
    );
};
