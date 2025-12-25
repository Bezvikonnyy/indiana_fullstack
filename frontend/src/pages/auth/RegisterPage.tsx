import React from "react";
import { AuthForm } from "./AuthForm";
import { Link, useNavigate } from "react-router-dom";
import {registerRequest} from "../../services/auth/registerRequest";

export const RegisterPage = () => {
    const navigate = useNavigate();

    const handleRegister = async ({ username, password, roleId, inviteCode }) => {
        try {
            await registerRequest(username, password, parseInt(roleId), inviteCode);
            alert("Регистрация прошла успешно!");
            navigate("/login"); // ✔️ перемещаем navigate сюда
        } catch (e) {
            alert("Ошибка при регистрации: " + e.message);
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
};
