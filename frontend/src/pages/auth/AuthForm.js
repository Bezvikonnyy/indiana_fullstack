import React, { useState } from 'react';
import './AuthPages.css';

function AuthForm({ mode, onSubmit }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [roleId, setRoleId] = useState('');
    const [inviteCode, setInviteCode] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        if (mode === 'register') {
            if (password !== confirmPassword) {
                alert('Пароли не совпадают!');
                return;
            }
            if (!roleId) {
                alert('Пожалуйста, выберите роль!');
                return;
            }
            if (roleId === '3' && !inviteCode.trim()) {
                alert('Инвайт-код обязателен для роли администратора!');
                return;
            }
        }

        onSubmit({ username, password, roleId, inviteCode });
    };

    return (
        <>
            <h2 className="auth-form-title">{mode === 'login' ? 'Вход' : 'Регистрация'}</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <input
                    className="auth-input"
                    type="text"
                    placeholder="Имя пользователя"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    className="auth-input"
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {mode === 'register' && (
                    <>
                        <input
                            className="auth-input"
                            type="password"
                            placeholder="Подтверждение пароля"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                        <select
                            className="auth-select"
                            value={roleId}
                            onChange={(e) => setRoleId(e.target.value)}
                            required
                        >
                            <option value="">-- выберите роль --</option>
                            <option value="1">Пользователь</option>
                            <option value="2">Автор</option>
                            <option value="3">Администратор</option>
                        </select>
                        {roleId === '3' && (
                            <input
                                className="auth-input"
                                type="text"
                                placeholder="Инвайт-код"
                                value={inviteCode}
                                onChange={(e) => setInviteCode(e.target.value)}
                                required
                            />
                        )}
                    </>
                )}
                <button type="submit" className="auth-button">
                    {mode === 'login' ? 'Войти' : 'Зарегистрироваться'}
                </button>
            </form>
        </>
    );
}

export default AuthForm;
