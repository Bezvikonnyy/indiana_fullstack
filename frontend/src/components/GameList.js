import React from 'react';
import { useNavigate } from 'react-router-dom';
import './GameList.css';

function GameList({ games, title, showDownload, renderActions }) {
    const navigate = useNavigate();

    if (!games) return null;

    if (games.length === 0) {
        return <p className="gameList-empty">Список пуст.</p>;
    }

    return (
        <div className="gameList-container">
            <h2 className="gameList-title">{title}</h2>
            <div className="gameList-grid">
                {games.map(game => (
                    <div
                        key={game.id}
                        className="gameList-card"
                        onClick={() => navigate(`/games/${game.id}`)}
                        style={{ cursor: "pointer" }}
                    >
                        <img
                            src={`http://localhost:8080${game.imageUrl}`}
                            alt={game.title}
                            className="gameList-image"
                        />
                        <h3 className="gameList-gameTitle">{game.title}</h3>
                        <p className="gameList-price">
                            {game.price ? `${game.price} грн` : "Бесплатно"}
                        </p>

                        {showDownload && (
                            <a
                                href={`http://localhost:8080${game.gameFileUrl}`}
                                download
                                onClick={(e) => e.stopPropagation()}
                            >
                                <button className="gameList-button">Скачать</button>
                            </a>
                        )}

                        {/* Здесь рендерим кастомные кнопки */}
                        {renderActions && (
                            <div
                                className="gameList-actions"
                                onClick={(e) => e.stopPropagation()} // чтобы кнопки не триггерили переход на игру
                            >
                                {renderActions(game)}
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default GameList;
