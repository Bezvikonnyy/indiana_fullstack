import { useNavigate, Link } from "react-router-dom";
import { AuthForm } from "./AuthForm";
import {loginRequest} from "../../services/auth/loginRequest";


export const LoginPage = () => {
    const navigate = useNavigate();

    const handleLogin = async ({ username, password }) => {
        try {
            await loginRequest(username, password);
            alert("Вход выполнен!");
            navigate("/home"); // ✔️ теперь работает
        } catch (e) {
            alert(e.message);
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
};
