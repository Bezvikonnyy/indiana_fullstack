import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [roleId, setRoleId] = useState('');
    const [inviteCode, setInviteCode] = useState('');

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

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

        const payload = {
            username,
            password,
            roleId: parseInt(roleId),
            inviteCode: inviteCode.trim()
        };

        try {
            const response = await fetch('http://localhost:8080/api/user/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                alert('Регистрация прошла успешно! Теперь можно войти.');
                navigate('/login'); // редирект на страницу логина
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
        <div>
            <h2>Регистрация</h2>
            <form onSubmit={handleRegister}>
                <div>
                    <label>Имя пользователя:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Пароль:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Подтверждение пароля:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Роль:</label>
                    <select
                        value={roleId}
                        onChange={(e) => setRoleId(e.target.value)}
                        required
                    >
                        <option value="">-- выберите роль --</option>
                        <option value="1">Пользователь</option>
                        <option value="2">Автор</option>
                        <option value="3">Администратор</option>
                    </select>
                </div>

                {roleId === '3' && (
                    <div>
                        <label>Инвайт-код:</label>
                        <input
                            type="text"
                            value={inviteCode}
                            onChange={(e) => setInviteCode(e.target.value)}
                        />
                    </div>
                )}

                <button type="submit">Зарегистрироваться</button>
            </form>
        </div>
    );
}

export default RegisterPage;
