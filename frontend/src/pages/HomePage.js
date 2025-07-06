import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function HomePage() {
    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();
    const isLoggedIn = !!localStorage.getItem('token'); // true/false

    useEffect(() => {
        fetch('http://localhost:8080/api/home')
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const handleLoginRedirect = () => {
        navigate('/login');
    };

    return (
        <div>
            <header style={{ display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
                {isLoggedIn ? (
                    <button onClick={handleLogout}>Выйти</button>
                ) : (
                    <button onClick={handleLoginRedirect}>Войти</button>
                )}
            </header>

            <div style={{ display: 'flex' }}>
                {/* Список категорий слева */}
                <div style={{ width: '200px', padding: '10px', position: 'sticky', top: '0' }}>
                    <h3>Категории</h3>
                    <ul style={{ listStyle: 'none', padding: 0 }}>
                        {categories.map(category => (
                            <li key={category.id}>{category.title}</li>
                        ))}
                    </ul>
                </div>

                {/* Игры по категориям */}
                <div style={{ flex: 1, padding: '10px' }}>
                    {categories.map(category => (
                        <div key={category.id} style={{ marginBottom: '40px' }}>
                            <h2>{category.title}</h2>
                            <div style={{ display: 'flex', overflowX: 'auto', gap: '10px' }}>
                                {category.games.map(game => (
                                    <div
                                        key={game.id}
                                        style={{
                                            minWidth: '150px',
                                            border: '1px solid #ccc',
                                            padding: '10px',
                                            borderRadius: '8px',
                                            textAlign: 'center',
                                            backgroundColor: '#f9f9f9',
                                        }}
                                    >
                                        <img
                                            src={game.imageUrl}
                                            alt={game.title}
                                            style={{ width: '100%', height: '100px', objectFit: 'cover', borderRadius: '4px' }}
                                        />
                                        <p style={{ marginTop: '10px' }}>{game.title}</p>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default HomePage;
