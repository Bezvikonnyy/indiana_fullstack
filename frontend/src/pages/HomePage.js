import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import './HomePage.css';

function HomePage() {
    const [categories, setCategories] = useState([]);
    const [userRole, setUserRole] = useState(null);
    const [favoriteGames, setFavoriteGames] = useState(new Set());
    const [isAuthorized, setIsAuthorized] = useState(false);
    const navigate = useNavigate();

    console.log(favoriteGames)

    // Загружаем категории
    useEffect(() => {
        fetch('http://localhost:8080/api/home')
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, []);

    // Получаем роль пользователя и проверяем авторизацию
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                let role = payload.role;
                if (typeof role === 'string' && role.startsWith('ROLE_')) {
                    role = role.substring(5);
                }
                setUserRole(role);
                setIsAuthorized(true);
            } catch (e) {
                console.error('Ошибка при декодировании токена', e);
                setIsAuthorized(false);
            }
        }
    }, []);

    // Загружаем избранное только если пользователь авторизован
    useEffect(() => {
        if (!isAuthorized) {
            setFavoriteGames(new Set());
            return;
        }

        const token = localStorage.getItem('token');
        fetch('http://localhost:8080/api/user/my_favorite_games', {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.json())
            .then(data => {
                // Приводим все id к числу для корректного сравнения
                const ids = new Set(data.map(game => Number(game.id)));
                setFavoriteGames(ids);
            })
            .catch(err => {
                console.error('Ошибка при загрузке избранного:', err);
                setFavoriteGames(new Set());
            });
    }, [isAuthorized]);

    const handleAddGame = (categoryId) => {
        navigate(`/games/create?categoryId=${categoryId}`);
    };

    const toggleFavorite = async (gameId) => {
        if (!isAuthorized) return alert('Сначала войдите в аккаунт');

        const token = localStorage.getItem('token');
        try {
            if (favoriteGames.has(gameId)) {
                await fetch(`http://localhost:8080/api/user/remove_favorite/${gameId}`, {
                    method: 'DELETE',
                    headers: { Authorization: `Bearer ${token}` },
                });
                setFavoriteGames(prev => {
                    const copy = new Set(prev);
                    copy.delete(gameId);
                    return copy;
                });
            } else {
                await fetch(`http://localhost:8080/api/user/add_favorite/${gameId}`, {
                    method: 'POST',
                    headers: { Authorization: `Bearer ${token}` },
                });
                setFavoriteGames(prev => {
                    const copy = new Set(prev);
                    copy.add(gameId);
                    return copy;
                });
            }
        } catch (err) {
            console.error('Ошибка при обновлении избранного:', err);
        }
    };

    return (
        <div className="home-page">
            <div className="home-content">
                <nav className="categories-list">
                    <h3>Категории</h3>
                    <ul>
                        {categories.map(category => (
                            <li key={category.id}>{category.title}</li>
                        ))}
                    </ul>
                </nav>

                <main className="games-list">
                    {categories.map(category => (
                        <section key={category.id} className="category-section">
                            <div className="category-header">
                                <h2>{category.title}</h2>
                                {(userRole === 'AUTHOR' || userRole === 'ADMIN') && (
                                    <button
                                        className="add-game-button"
                                        onClick={() => handleAddGame(category.id)}
                                        title="Добавить игру"
                                    >
                                        +
                                    </button>
                                )}
                            </div>

                            <div className="games-row">
                                {category.games.map(game => (
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
                                        {isAuthorized && (
                                            <span
                                                className={`favorite-btn-home ${favoriteGames.has(Number(game.id)) ? 'active' : 'not-active'}`}
                                                onClick={() => toggleFavorite(Number(game.id))}
                                            >
                                                ♥
                                            </span>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </section>
                    ))}
                </main>
            </div>
        </div>
    );
}

export default HomePage;
