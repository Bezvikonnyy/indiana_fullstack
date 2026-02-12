import {FC} from 'react';
import './DeleteNewsButton.css';
import {useNavigate} from "react-router-dom";
import {TrashIcon} from "../../../assets/TrashIcon";
import {postDeleteNews} from "../../../services/news/postDeleteNews";

interface DeleteNewsButtonProps {
    id: string;
}

export const DeleteNewsButton: FC<DeleteNewsButtonProps> = ({id}) => {
    const navigate = useNavigate();

    const handleClick = async () => {
        if (!window.confirm('Удалить эту новость?')) return;
        const res = await postDeleteNews(id);
        if(!res.success) { alert(res.error.message)}
        else {
            alert('Новость удалена!');
            navigate('/home');
        }
    };

    return (
        <button onClick={handleClick} className={"delete-news-button"}>
            <TrashIcon/>
        </button>
    );
}
