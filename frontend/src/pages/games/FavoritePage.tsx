import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './FavoritePage.css';

export const FavoritePage = () => {
    const [favoriteGames, setFavoriteGames] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) return;
        fetch('http://localhost:8080/api/user/my_favorite_games', {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.json())
            .then(data => setFavoriteGames(data))
            .catch(err => console.error('Ошибка при загрузке избранного:', err));
    }, []);

    const removeFavorite = async (gameId) => {
        const token = localStorage.getItem('token');
        if (!token) return alert('Сначала войдите в аккаунт');

        try {
            await fetch(`http://localhost:8080/api/user/remove_favorite/${gameId}`, {
                method: 'DELETE',
                headers: { Authorization: `Bearer ${token}` },
            });
            setFavoriteGames(prev => prev.filter(game => game.id !== gameId));
        } catch (err) {
            console.error('Ошибка при удалении из избранного:', err);
        }
    };

    if (favoriteGames.length === 0) {
        return (
            <div className="favorite-page">
                <h2>У вас пока нет избранных игр</h2>
            </div>
        );
    }

    return (
        <div className="favorite-page">
            <h2>Избранные игры</h2>
            <div className="favorite-games-row">
                {favoriteGames.map(game => (
                    <div key={game.id} className="game-card">
                        <Link
                            to={`/games/${game.id}`}
                            style={{ textDecoration: 'none', color: 'inherit', width: '100%' }}
                        >
                            <img
                                src={game.imageUrl}
                                alt={game.title}
                                className="game-image"
                            />
                            <p className="game-title">{game.title}</p>
                        </Link>
                        <span
                            className="favorite-btn active"
                            onClick={() => removeFavorite(game.id)}
                        >
                            ♥
                        </span>
                    </div>
                ))}
            </div>
        </div>
    );
}
