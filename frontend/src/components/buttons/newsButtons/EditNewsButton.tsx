import {FC} from 'react';
import './EditNewsButton.css';
import {useNavigate} from "react-router-dom";
import {PencilIcon} from "../../../assets/PencilIcon";

interface EditNewsButtonProps {
    id: string;
}

export const EditNewsButton: FC<EditNewsButtonProps> = ({id}) => {
    const navigate = useNavigate();

    const handleEdit = () => navigate(`/news/edit/${id}`);


    return (
        <button onClick={handleEdit} className={"edit-news-button"}>
            <PencilIcon/>
        </button>
    );
}