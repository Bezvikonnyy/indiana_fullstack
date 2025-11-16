import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import GameList from '../../components/GameList';

export const CreatedGamesPage = () => {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        fetch("http://localhost:8080/api/user/my_game", {
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
                console.error("Ошибка при загрузке созданных игр:", err);
                alert("Не удалось загрузить список игр");
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="gameList-loading">Загрузка...</p>;

    return (
        <GameList
            games={games}
            title="Мои созданные игры"
            showDownload={false}
            renderActions={(game) => (
                <>
                    <button onClick={() => navigate(`/games/edit/${game.id}`)}>Редактировать</button>
                    <button
                        onClick={async () => {
                            if (!window.confirm('Удалить эту игру?')) return;
                            const token = localStorage.getItem('token');
                            const res = await fetch(`http://localhost:8080/api/game/delete/${game.id}`, {
                                method: 'DELETE',
                                headers: { Authorization: `Bearer ${token}` },
                            });
                            if (res.ok) setGames(prev => prev.filter(g => g.id !== game.id));
                        }}
                        style={{ marginLeft: '10px' }}
                    >
                        Удалить
                    </button>
                </>
            )}
        />
    );
}
