import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getUserId, hasRole } from '../../utils/auth';

function GameDetailsPage() {
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

    if (!game) return <p style={{ textAlign: 'center', marginTop: '2rem' }}>Загрузка...</p>;

    const isAuthor = game.authorId === currentUserId;
    const isAdmin = hasRole(['ADMIN']);

    return (
        <div style={styles.container}>
            <h2 style={styles.title}>{game.title}</h2>
            <img
                src={`http://localhost:8080${game.imageUrl}`}
                alt={game.title}
                style={styles.image}
            />
            <p style={styles.description}>{game.details}</p>

            <p style={styles.categories}>
                <strong>Категории: </strong>
                {Array.isArray(game.categories) && game.categories.length > 0
                    ? game.categories.map(cat => cat.title).join(', ')
                    : 'Нет категорий'}
            </p>

            <div style={styles.buttons}>
                <a href={`http://localhost:8080${game.gameFileUrl}`} download>
                    <button style={styles.button}>Скачать</button>
                </a>
                {(isAuthor || isAdmin) && (
                    <>
                        <button style={styles.button} onClick={handleEdit}>Редактировать</button>
                        <button
                            style={{ ...styles.button, backgroundColor: '#b71c1c' }}
                            onClick={handleDelete}
                        >
                            Удалить
                        </button>
                    </>
                )}
            </div>
        </div>
    );
}

const styles = {
    container: {
        maxWidth: 700,
        margin: '40px auto',
        padding: '2rem',
        backgroundColor: '#fff',
        borderRadius: 8,
        boxShadow: '0 8px 20px rgba(0,0,0,0.1)',
        fontFamily: 'Arial, sans-serif',
        textAlign: 'center', // выравнивание содержимого по центру
    },
    title: {
        fontSize: '2.5rem',
        marginBottom: '1rem',
        color: '#2e7d32',
        fontWeight: '700',
    },
    image: {
        width: '100%',
        maxWidth: '600px',
        height: 'auto',        // сохраняет пропорции изображения
        objectFit: 'contain',  // изображение масштабируется, не обрезается
        borderRadius: 6,
        marginBottom: '1rem',
    },
    description: {
        marginBottom: '1.5rem',
        fontSize: '1.1rem',
        color: '#333',
    },
    categories: {
        fontSize: '1rem',
        marginBottom: '2rem',
        color: '#555',
    },
    buttons: {
        display: 'flex',
        justifyContent: 'center',
        gap: '15px',
        flexWrap: 'wrap',
    },
    button: {
        padding: '0.6rem 1.5rem',
        backgroundColor: '#2e7d32',
        color: '#fff',
        border: 'none',
        borderRadius: 5,
        cursor: 'pointer',
        fontWeight: 'bold',
        fontSize: '1rem',
        transition: 'background-color 0.3s ease',
    },
};

export default GameDetailsPage;
