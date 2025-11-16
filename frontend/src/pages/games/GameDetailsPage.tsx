import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getUserId, hasRole } from '../../utils/auth';
import CommentSection from '../../components/CommentSection';
import FavoriteButton from '../../components/FavoriteButton';
import './GameDetailsPage.css';

export const GameDetailsPage = () => {
    const { id } = useParams();
    const [game, setGame] = useState(null);
    const navigate = useNavigate();
    const currentUserId = getUserId();

    useEffect(() => {
        fetch(`http://localhost:8080/api/game/${id}`)
            .then(res => res.json())
            .then(data => setGame(data))
            .catch(err => {
                console.error('Ошибка при загрузке игры:', err);
                alert('Не удалось загрузить игру');
            });
    }, [id]);

    const handleEdit = () => navigate(`/games/edit/${id}`);

    const handleDelete = async () => {
        if (!window.confirm('Удалить эту игру?')) return;

        try {
            const token = localStorage.getItem('token');
            const res = await fetch(`http://localhost:8080/api/game/delete/${id}`, {
                method: 'DELETE',
                headers: { Authorization: `Bearer ${token}` },
            });

            if (res.ok) {
                alert('Игра удалена.');
                navigate('/home');
            } else {
                const text = await res.text();
                alert('Ошибка: ' + text);
            }
        } catch (err) {
            alert('Ошибка сети: ' + err.message);
        }
    };

    const handleAddToCart = async () => {
        try {
            const token = localStorage.getItem("token");
            const res = await fetch("http://localhost:8080/api/cart/add", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({ gameId: id }),
            });

            if (res.ok) {
                alert(`Игра "${game.title}" добавлена в корзину!`);
            } else {
                const text = await res.text();
                alert("Ошибка при добавлении: " + text);
            }
        } catch (err) {
            alert("Ошибка сети: " + err.message);
        }
    };

    if (!game) return <p className="loading-text">Загрузка...</p>;

    const isAuthor = game.authorId === currentUserId;
    const isAdmin = hasRole(['ADMIN']);

    return (
        <>
        <div className="game-details-container">
            <div className="left-container-game-details">

                <img
                    src={`http://localhost:8080${game.imageUrl}`}
                    alt={game.title}
                    className="game-image-game-details"
                />

                <div className="game-buttons-game-details">
                    <a href={`http://localhost:8080${game.gameFileUrl}`} download>
                        <button className="button-game-details">Скачать</button>
                    </a>
                    <button className="button add-cart-button-game-details" onClick={handleAddToCart}>
                        Добавить в корзину
                    </button>
                        {(isAuthor || isAdmin) && (
                        <>
                            <button className="button" onClick={handleEdit}>Редактировать</button>
                            <button className="button delete-button-game-details" onClick={handleDelete}>Удалить</button>
                        </>
                    )}
                    <div className="favorite-wrapper-game-details">
                        <FavoriteButton gameId={game.id} className="" />
                    </div>
                </div>

            </div>
            <div className="right-container-game-details">
                <h2 className="game-title-game-details">{game.title}</h2>

                <p className="game-description-game-details">{game.details}</p>

                <p className="game-categories-game-details">
                    <strong>Категории: </strong>
                    {Array.isArray(game.categories) && game.categories.length > 0
                        ? game.categories.map(cat => cat.title).join(', ')
                        : 'Нет категорий'}
                </p>

                <p className="game-price-game-details">
                    <strong>Цена: </strong>
                    {game.price ? `${game.price} грн` : 'Бесплатно'}
                </p>
            </div>
        </div>
        <div className="comments-section-game-details">
            <CommentSection gameId={id} />
        </div>
        </>
    );
}
