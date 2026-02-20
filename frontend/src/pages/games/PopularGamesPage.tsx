import React, { useEffect, useState } from "react";
import { getPopularGames } from "../../services/games/getPopularGames";
import { GameCard } from "../../components/GameCard";
import { GamesPageDto } from "../../types/games/GamesPageDto";
import "./PopularGamesPage.css";

export const PopularGamesPage: React.FC = () => {
    const [gamesPage, setGamesPage] = useState<GamesPageDto | null>(null);
    const [page, setPage] = useState(0);

    useEffect(() => {
        const fetchGames = async () => {
            const res = await getPopularGames(page, 20);

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
        <div className="popular-games-container">
            <h1 className="popular-games-title">Популярные</h1>

            <div className="popular-games-grid">
                {gamesPage.content.map(game => (
                    <GameCard key={game.id} game={game} />
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
