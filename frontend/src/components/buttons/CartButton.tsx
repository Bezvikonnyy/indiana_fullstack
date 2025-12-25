import React, {useState, FC} from 'react';
import './CartButton.css';
import {postInCartToggle} from "../../services/buttons/postInCartToggle";
import {CartIcon} from "../../assets/CartIcon";

interface CartButtonProps {
    gameId: number;
    isInCart: boolean;
}

export const CartButton: FC<CartButtonProps> = ({ gameId, isInCart }) => {
    const [active, setActive] = useState(isInCart);

    const handleClick = () => {
        postInCartToggle(gameId, active, setActive);
    }

    return (
        <button onClick={handleClick} className={active ? 'cart-button active' : 'cart-button'}>
            <CartIcon />
        </button>
    );
}
