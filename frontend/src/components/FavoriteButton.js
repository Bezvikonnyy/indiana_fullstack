import React, { useState, useEffect } from 'react';
import './FavoriteButton.css';

function FavoriteButton({ gameId, initialActive = false, onChange }) {
    const [isActive, setIsActive] = useState(initialActive);
    const [isAuthorized, setIsAuthorized] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsAuthorized(!!token);
    }, []);

    const toggleFavorite = async () => {
        if (!isAuthorized) {
            return alert('Сначала войдите в аккаунт');
        }

        const token = localStorage.getItem('token');
        try {
            if (isActive) {
                await fetch(`http://localhost:8080/api/user/remove_favorite/${gameId}`, {
                    method: 'DELETE',
                    headers: { Authorization: `Bearer ${token}` },
                });
                setIsActive(false);
                onChange && onChange(false);
            } else {
                await fetch(`http://localhost:8080/api/user/add_favorite/${gameId}`, {
                    method: 'POST',
                    headers: { Authorization: `Bearer ${token}` },
                });
                setIsActive(true);
                onChange && onChange(true);
            }
        } catch (err) {
            console.error('Ошибка при обновлении избранного:', err);
        }
    };

    return (
        isAuthorized && (
            <span
                className={`favorite-btn ${isActive ? 'active' : 'not-active'}`}
                onClick={toggleFavorite}
            >
                ❤
            </span>
        )
    );
}

export default FavoriteButton;
