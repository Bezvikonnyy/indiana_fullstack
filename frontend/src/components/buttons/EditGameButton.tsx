import {FC} from 'react';
import './EditGameButton.css';
import {useNavigate} from "react-router-dom";
import {PencilIcon} from "../../assets/PencilIcon";

interface EditGameButtonProps {
    id: string;
}

export const EditGameButton: FC<EditGameButtonProps> = ({id}) => {
    const navigate = useNavigate();

    const handleEdit = () => navigate(`/games/edit/${id}`);


    return (
        <button onClick={handleEdit} className={"edit-game-button"}>
            <PencilIcon/>
        </button>
    );
}
