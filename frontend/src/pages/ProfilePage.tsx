import React, { useEffect, useState } from 'react';
import './ProfilePage.css';

export const ProfilePage = () => {
    const [profile, setProfile] = useState(null);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/user/profile', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            },
        })
            .then(res => res.json())
            .then(data => {
                setProfile(data);
                setUsername(data.username);
            })
            .catch(() => setMessage('Ошибка загрузки профиля'));
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const res = await fetch('http://localhost:8080/api/user/edit_profile', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            },
            body: JSON.stringify({
                username: username,
                password: password || null, // если пустой, бэкенд может проигнорировать
            }),
        });

        if (res.ok) {
            setMessage('Профиль обновлён');
            setPassword('');
        } else {
            const err = await res.text();
            setMessage('Ошибка: ' + err);
        }
    };

    if (!profile) return <p className="center">Загрузка профиля...</p>;

    return (
        <div className="profile-container">
            <h2>Профиль</h2>

            <form onSubmit={handleSubmit} className="profile-form">
                <label>Имя пользователя:</label>
                <input
                    type="text"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    required
                />

                <label>Новый пароль:</label>
                <input
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    placeholder="Оставьте пустым, если не менять"
                />

                <button type="submit">Сохранить</button>
            </form>

            {message && <p className="message">{message}</p>}
        </div>
    );
}
