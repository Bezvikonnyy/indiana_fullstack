import {useState, FC} from 'react';
import './PurchasedStatus.css';
import {DollarIcon} from "../../assets/DollarIcon";


interface PurchasedStatusProps {
    isPurchased: boolean;
}

export const PurchasedStatus: FC<PurchasedStatusProps> = ({isPurchased }) => {
    const [active] = useState(isPurchased);

    return (
        <div className={active ? 'purchased active' : 'purchased'}>
            <DollarIcon/>
        </div>
    );
}
