// RegisterPage.jsx
import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import AuthForm from './AuthForm';

function RegisterPage() {
    const navigate = useNavigate();

    const handleRegister = async ({ username, password, roleId, inviteCode }) => {
        const payload = {
            username,
            password,
            roleId: parseInt(roleId),
            inviteCode: inviteCode.trim(),
        };

        try {
            const response = await fetch('http://localhost:8080/api/user/registration', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                alert('Регистрация прошла успешно! Теперь можно войти.');
                navigate('/login');
            } else {
                let errorMessage = 'Неизвестная ошибка';
                try {
                    const contentType = response.headers.get('content-type');
                    if (contentType && contentType.includes('application/json')) {
                        const errorData = await response.json();
                        errorMessage = errorData.message || JSON.stringify(errorData);
                    } else {
                        errorMessage = await response.text();
                    }
                } catch {
                    // Игнорируем ошибки парсинга
                }
                alert(`Ошибка при регистрации: ${errorMessage}`);
            }
        } catch (error) {
            alert('Ошибка сети или сервера: ' + error.message);
        }
    };

    return (
        <div className="auth-page-wrapper">
            <div className="auth-form-container">
                <AuthForm mode="register" onSubmit={handleRegister} />
                <p className="auth-switch-link">
                    Уже есть аккаунт? <Link to="/login">Войти</Link>
                </p>
            </div>
        </div>
    );
}

export default RegisterPage;
