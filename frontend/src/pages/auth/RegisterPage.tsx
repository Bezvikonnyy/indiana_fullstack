import React, {useState} from "react";
import { AuthForm } from "./AuthForm";
import { Link, useNavigate } from "react-router-dom";
import {registerRequest} from "../../services/auth/registerRequest";

export const RegisterPage = () => {
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleRegister = async (payload: PayloadAuth) => {
        const result = await registerRequest(payload);
        if (!result.success) {
            setError(result.error.message);
            return;
        } else {
            alert("Регистрация прошла успешно!");
            navigate("/login");
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
