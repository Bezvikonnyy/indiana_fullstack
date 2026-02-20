import React, { useEffect, useState } from "react";
import { getDiscountGames } from "../../services/games/getDiscountGames";
import { GameCard } from "../../components/GameCard";
import "./DiscountGamesPage.css";
import { GamesPageDto } from "../../types/games/GamesPageDto";

export const DiscountGamesPage: React.FC = () => {
    const [gamesPage, setGamesPage] = useState<GamesPageDto | null>(null);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchGames = async () => {
            setLoading(true);
            const res = await getDiscountGames(page, 20);

            if (!res.success) {
                alert(res.error.message);
            } else {
                setGamesPage(res.data);
            }
            setLoading(false);
        };

        void fetchGames();
    }, [page]);

    if (loading) return <p>Загрузка...</p>;
    if (!gamesPage || gamesPage.content.length === 0) return <p>Нет игр с акцией</p>;

    return (
        <div className="discount-games-container">
            <h1 className="discount-games-title">Акции</h1>

            <div className="discount-games-grid">
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
