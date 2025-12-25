import { useNavigate } from 'react-router-dom';
import './GameForm.css';
import {GameForm} from "./GameForm";
import {createGame} from "../../services/games/createGame";

export const CreateGamePage = () =>{
    const navigate = useNavigate();

    const handleSubmit = (formData) => {createGame(formData,navigate)};

    return (
        <div className="game-form-container">
            <GameForm onSubmit={handleSubmit} submitText="Сохранить" />
        </div>
    );
}
