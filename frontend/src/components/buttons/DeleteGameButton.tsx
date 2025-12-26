import {FC} from 'react';
import './DeleteGameButton.css';
import {postDeleteGame} from "../../services/buttons/postDeleteGame";
import {useNavigate} from "react-router-dom";
import {TrashIcon} from "../../assets/TrashIcon";

interface DeleteGameButtonProps {
    id: string;
}

export const DeleteGameButton: FC<DeleteGameButtonProps> = ({id}) => {
    const navigate = useNavigate();

    const handleClick = async () => {
        if (!window.confirm('Удалить эту игру?')) return;
        const res = await postDeleteGame(id);
        if(!res.success) { alert(res.error.message)}
        else {
            alert('Игра удалена!');
            navigate('/home');
        }
    };

    return (
        <button onClick={handleClick} className={"delete-game-button"}>
            <TrashIcon/>
        </button>
    );
}
