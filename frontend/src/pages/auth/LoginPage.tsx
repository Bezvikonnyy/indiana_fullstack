import {useNavigate, Link} from "react-router-dom";
import {AuthForm} from "./AuthForm";
import {loginRequest} from "../../services/auth/loginRequest";
import React, {useState} from "react";


export const LoginPage = () => {
    const [error, setError] = useState<string | null>(null);

    const navigate = useNavigate();

    const handleLogin = async ({username, password}) => {
        const result = await loginRequest(username, password);
        if (!result.success) {
            setError(result.error.message);
            return;
        }
        localStorage.setItem('token', result.data.token);
        console.log('Успешный логин', result.data);
        navigate("/home");
    };

    return (
        <div className="auth-page-wrapper">
            <div className="auth-form-container">
                <AuthForm mode="login" onSubmit={handleLogin}/>
                {error && (
                    <p className="auth-error-message">Не верный логин либо пароль!</p>
                )}
                <p className="auth-switch-link">
                    Нет аккаунта? <Link to="/register">Зарегистрироваться</Link>
                </p>
            </div>
        </div>
    );
};
