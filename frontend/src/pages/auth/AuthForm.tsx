import React, { useState } from 'react';
import './AuthPages.css';

export const AuthForm = ({ mode, onSubmit }) => {
    const [confirmPassword, setConfirmPassword] = useState<string | null>('');
    const [data, setData] = useState<PayloadAuth>({
        username: '',
        password: '',
        roleId: 0,
        inviteCode: '',
    });

    const handleSubmit = (e) => {
        e.preventDefault();

        if (mode === 'register') {
            if (data?.password !== confirmPassword) {
                alert('Пароли не совпадают!');
                return;
            }
            if (!data.roleId) {
                alert('Пожалуйста, выберите роль!');
                return;
            }
            if (data.roleId === 3 && !data.inviteCode.trim()) {
                alert('Инвайт-код обязателен для роли администратора!');
                return;
            }
        }

        onSubmit(data);
    };

    return (
        <>
            <h2 className="auth-form-title">{mode === 'login' ? 'Вход' : 'Регистрация'}</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <input
                    className="auth-input"
                    type="text"
                    placeholder="Имя пользователя"
                    value={data?.username}
                    onChange={(e) => setData(prev => ({ ...prev, username: e.target.value }))}
                    required
                />
                <input
                    className="auth-input"
                    type="password"
                    placeholder="Пароль"
                    value={data?.password}
                    onChange={(e) => setData(prev => ({ ...prev, password: e.target.value}))}
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
                            value={data?.roleId}
                            onChange={(e) => setData(prev => ({ ...prev, roleId: Number(e.target.value)}))}
                            required
                        >
                            <option value="">-- выберите роль --</option>
                            <option value={1}>Пользователь</option>
                            <option value={2}>Автор</option>
                            <option value={3}>Администратор</option>
                        </select>
                        {data?.roleId === 3 && (
                            <input
                                className="auth-input"
                                type="text"
                                placeholder="Инвайт-код"
                                value={data.inviteCode}
                                onChange={(e) => setData(prev => ({ ...prev, inviteCode: e.target.value}))}
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
