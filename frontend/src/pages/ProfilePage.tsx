import React, { useEffect, useState } from 'react';
import './ProfilePage.css';
import {getProfile} from "../services/users/getProfile";
import {postEditProfile} from "../services/users/postEditProfile";

export const ProfilePage = () => {
    const [profile, setProfile] = useState(null);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const data = await getProfile();
                setProfile(data);
                setUsername(data.username);
            } catch (err) {
                setMessage('Ошибка: ' + err.message);
            }
        };

        void loadProfile();
    }, []);


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await postEditProfile(username, password);
            setMessage('Профиль обновлён');
            setPassword('');
        } catch (err) {
            setMessage('Ошибка: ' + err.message);
        }
    };


    if (!profile) return <p className="center">Загрузка профиля...</p>;

    return (
        <div className="profile-container">
            <h2 className={"profile-form-h2"}>Профиль</h2>

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
