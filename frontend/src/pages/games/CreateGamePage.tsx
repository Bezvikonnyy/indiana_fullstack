import {useNavigate} from 'react-router-dom';
import './GameForm.css';
import {GameForm} from "./GameForm";
import {createGame} from "../../services/games/createGame";

export const CreateGamePage = () => {
    const navigate = useNavigate();

    const handleSubmit = (formData) => {
        const fetchCreateGame = async () => {
            const res = await createGame(formData);
            if(!res.success) {alert(res.error.message)}
            else {
                alert("Игра создана!");
                navigate('/home');
            }
        }
        void fetchCreateGame();
    };

    return (
        <div className="game-form-container">
            <GameForm onSubmit={handleSubmit} submitText="Сохранить"/>
        </div>
    );
}
