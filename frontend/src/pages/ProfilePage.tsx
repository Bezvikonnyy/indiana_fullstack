import React, {useEffect, useState} from 'react';
import './ProfilePage.css';
import {getProfile} from "../services/users/getProfile";
import {postEditProfile} from "../services/users/postEditProfile";

export const ProfilePage = () => {
    const [profile, setProfile] = useState<ProfileDto>();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    useEffect(() => {
        const loadProfile = async () => {
            const res = await getProfile();
            if (!res.success) {
                console.log(res.error.message)
            } else {
                setProfile(res.data);
                setUsername(res.data.username)
            }
        };

        void loadProfile();
    }, []);


    const handleSubmit = async (e) => {
        e.preventDefault();

        const res = await postEditProfile(username, password);
        if(!res.success) {
            console.log(res.error.message)
        } else {
            setMessage('Профиль обновлен!');
            setProfile(res.data);
        }
    };

    const roleLabel = (() => {
        switch (profile?.role.title) {
            case 'ROLE_ADMIN':
                return 'Администратор';
            case 'ROLE_AUTHOR':
                return 'Автор';
            case 'ROLE_USER':
                return 'Пользователь';
            default:
                return 'Неизвестно';
        }
    })();


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

                <label>Роль:</label>
                <div>
                    <p className="profile-info-role">{roleLabel}</p>
                </div>

                <label>Дата создания профиля:</label>
                <p className="profile-info-create">{new Date(profile.createdAt).toLocaleDateString()}</p>

                <button type="submit">Сохранить</button>
            </form>

            {message && <p className="message">{message}</p>}
        </div>
    );
}
