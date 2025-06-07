// src/components/HomePage.jsx
import React, { useEffect, useState } from 'react';

export function HomePage() {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('/api/home')
            .then(res => {
                if (!res.ok) throw new Error('Ошибка при загрузке данных');
                return res.json();
            })
            .then(data => {
                setCategories(data);
                setLoading(false);
            })
            .catch(err => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Загрузка...</div>;
    if (error) return <div>Ошибка: {error}</div>;

    return (
        <div>
            <h1>Категории игр</h1>
            {categories.map(category => (
                <div key={category.id} style={{ marginBottom: '20px' }}>
                    <h2>{category.title}</h2>
                    <ul>
                        {category.games.map(game => (
                            <li key={game.id}>
                                <img src={game.imageUrl} alt={game.title} width={100} />
                                <span>{game.title}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    );
}

export default HomePage;
