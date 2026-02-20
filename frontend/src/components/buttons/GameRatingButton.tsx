import { useState, FC } from "react";
import './GameRatingButton.css';
import { StarIcon } from "../../assets/StarIcon";
import { postGameRating } from "../../services/games/postGameRating";

interface GameRatingButtonProps {
    gameId: number;
    initialRating: number; // текущая оценка пользователя, 0 если нет
}

export const GameRatingButton: FC<GameRatingButtonProps> = ({ gameId, initialRating }) => {
    const [rating, setRating] = useState(initialRating);
    const [hover, setHover] = useState(0); // для визуального эффекта при наведении

    const handleClick = async (value: number) => {
        const res = await postGameRating(gameId, value);
        if (res.success) {
            setRating(value);
        } else {
            console.log(res.error.message);
        }
    };

    return (
        <div className="game-rating">
            {[1,2,3,4,5].map(value => (
                <button
                    key={value}
                    type="button"
                    className={`star-button ${value <= (hover || rating) ? 'active' : ''}`}
                    onClick={() => handleClick(value)}
                    onMouseEnter={() => setHover(value)}
                    onMouseLeave={() => setHover(0)}
                >
                    <StarIcon />
                </button>
            ))}
        </div>
    );
};
