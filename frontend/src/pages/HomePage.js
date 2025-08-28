// HomePage.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import FavoriteButton from '../components/FavoriteButton';
import './HomePage.css';

function HomePage() {
    const [categories, setCategories] = useState([]);
    const [userRole, setUserRole] = useState(null);
    const [isAuthorized, setIsAuthorized] = useState(false);
    const [favoriteGames, setFavoriteGames] = useState(new Set());
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/api/home')
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, []);

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

                // Загружаем избранное
                fetch('http://localhost:8080/api/user/my_favorite_games', {
                    headers: { Authorization: `Bearer ${token}` },
                })
                    .then(res => res.json())
                    .then(data => {
                        setFavoriteGames(new Set(data.map(g => Number(g.id))));
                    });
            } catch (e) {
                console.error('Ошибка при декодировании токена', e);
                setIsAuthorized(false);
            }
        }
    }, []);

    const handleAddGame = (categoryId) => {
        navigate(`/games/create?categoryId=${categoryId}`);
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
                                            <FavoriteButton
                                                gameId={game.id}
                                                initialActive={favoriteGames.has(Number(game.id))
                                            }
                                            />
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
