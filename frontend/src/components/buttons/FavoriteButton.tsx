import {useState, FC} from 'react';
import './FavoriteButton.css';
import {postFavoriteToggle} from "../../services/buttons/postFavoriteToggle";
import {HeartIcon} from "../../assets/HertIcon";

interface FavoriteButtonProps {
    gameId: number;
    isFavorite: boolean;
}

export const FavoriteButton: FC<FavoriteButtonProps> = ({ gameId, isFavorite }) => {
    const [active, setActive] = useState(isFavorite);

    const handleClick = async () => {
        const res = await postFavoriteToggle(gameId);
        if(res.success) { setActive(res.data.isFavorite)}
        else {console.log(res.error.message)}
    }

    return (
        <button onClick={handleClick} className={active ? 'favorite active' : 'favorite'}>
            <HeartIcon/>
        </button>
    );
}
