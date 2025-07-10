import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getUserId, hasRole } from '../../utils/auth'; // ← путь зависит от структуры, исправь при необходимости

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

    const handleEdit = () => {
        navigate(`/games/edit/${id}`);
    };

    const handleDelete = async () => {
        if (!window.confirm('Удалить эту игру?')) return;

        try {
            const token = localStorage.getItem('token');
            const res = await fetch(`http://localhost:8080/api/game/delete/${id}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
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

    const handleDownload = () => {
        window.open(game.gameFileUrl, '_blank');
    };

    if (!game) return <p style={{ textAlign: 'center', marginTop: '2rem' }}>Загрузка...</p>;

    const isAuthor = game.author?.id === currentUserId;
    const isAdmin = hasRole(['ADMIN']);

    return (
        <div style={styles.container}>
            <h2 style={styles.title}>{game.title}</h2>
            <img src={game.imageUrl} alt={game.title} style={styles.image} />
            <p style={styles.description}>{game.details}</p>
            <p><strong>Категории:</strong> {game.categories.map(cat => cat.title).join(', ')}</p>

            <div style={styles.buttons}>
                <button style={styles.button} onClick={handleDownload}>Скачать</button>
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
        maxWidth: '600px',
        margin: '40px auto',
        padding: '2rem',
        backgroundColor: '#fff',
        borderRadius: '8px',
        boxShadow: '0 8px 20px rgba(0, 0, 0, 0.1)',
        fontFamily: 'Arial, sans-serif',
    },
    title: {
        fontSize: '2rem',
        marginBottom: '1rem',
        color: '#2e7d32',
    },
    image: {
        width: '100%',
        height: '250px',
        objectFit: 'cover',
        borderRadius: '6px',
        marginBottom: '1rem',
    },
    description: {
        marginBottom: '1rem',
    },
    buttons: {
        display: 'flex',
        gap: '10px',
        flexWrap: 'wrap',
    },
    button: {
        padding: '0.6rem 1.2rem',
        backgroundColor: '#2e7d32',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        fontWeight: 'bold',
    },
};

export default GameDetailsPage;
