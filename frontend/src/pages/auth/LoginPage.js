// LoginPage.jsx
import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import AuthForm from './AuthForm';

function LoginPage() {
    const navigate = useNavigate();

    const handleLogin = async ({ username, password }) => {
        try {
            const response = await fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('token', data.token);
                alert('Вход выполнен!');
                navigate('/home');
            } else {
                alert('Неверные данные!');
            }
        } catch (error) {
            alert('Ошибка сети или сервера: ' + error.message);
        }
    };

    return (
        <div className="auth-page-wrapper">
            <div className="auth-form-container">
                <AuthForm mode="login" onSubmit={handleLogin} />
                <p className="auth-switch-link">
                    Нет аккаунта? <Link to="/register">Зарегистрироваться</Link>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;
