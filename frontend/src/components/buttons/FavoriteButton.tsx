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

    const handleClick = () => {
        postFavoriteToggle(gameId, active, setActive);
    }

    return (
        <button onClick={handleClick} className={active ? 'favorite active' : 'favorite'}>
            <HeartIcon/>
        </button>
    );
}
