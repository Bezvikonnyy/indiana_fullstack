import React, { useEffect, useState } from 'react';
import './PurchasedGamesPage.css';

function PurchasedGamesPage() {
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

    if (loading) {
        return <p className="purchased-loading">Загрузка...</p>;
    }

    if (games.length === 0) {
        return <p className="purchased-empty">У вас пока нет купленных игр.</p>;
    }

    return (
        <div className="purchased-container">
            <h2 className="purchased-title">Мои игры</h2>
            <div className="purchased-grid">
                {games.map(game => (
                    <div key={game.id} className="purchased-card">
                        <img
                            src={`http://localhost:8080${game.imageUrl}`}
                            alt={game.title}
                            className="purchased-image"
                        />
                        <h3 className="purchased-gameTitle">{game.title}</h3>
                        <p className="purchased-price">
                            {game.price ? `${game.price} грн` : "Бесплатно"}
                        </p>
                        <a href={`http://localhost:8080${game.gameFileUrl}`} download>
                            <button className="purchased-button">Скачать</button>
                        </a>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default PurchasedGamesPage;
