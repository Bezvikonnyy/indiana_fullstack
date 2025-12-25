import React, { useEffect, useState } from 'react';
// import GameList from '../../components/GameList';

export const PurchasedGamesPage = () => {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        fetch("http://localhost:8080/api/user/purchased_game", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(res => {
                if (!res.ok) throw new Error("Ошибка загрузки игр");
                return res.json();
            })
            .then(data => {
                setGames(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Ошибка при загрузке купленных игр:", err);
                alert("Не удалось загрузить список игр");
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="gameList-loading">Загрузка...</p>;

    return <GameList games={games} title="Мои игры" showDownload={true} />;
}
