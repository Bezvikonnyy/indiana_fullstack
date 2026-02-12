import React, {useEffect, useState} from "react";
import "./ShowcaseSection.css";
import {GameCardType} from "../../constant/GameCardType";
import {NavLink} from "react-router-dom";

interface ShowcaseSectionProps {
    items: GameCardType[];
    autoPlay?: boolean;
    interval?: number;
}

export const ShowcaseSection: React.FC<ShowcaseSectionProps> = ({
                                                                    items,
                                                                    autoPlay = true,
                                                                    interval = 5000
                                                                }) => {
    const [activeIndex, setActiveIndex] = useState(0);

    useEffect(() => {
        if (!autoPlay || items.length <= 1) return;

        const timer = setInterval(() => {
            setActiveIndex(prev =>
                prev === items.length - 1 ? 0 : prev + 1
            );
        }, interval ?? 5000);

        return () => clearInterval(timer);
    }, [items.length, autoPlay, interval]);

    if (!items || items.length === 0) return null;

    const activeItem = items[activeIndex];

    return (
        <div className="showcase">
                <div className="showcase-main">
                    <NavLink to={`/games/${activeItem.id}`}>
                    <img
                        src={`http://localhost:8080${activeItem.imageUrl}`}
                        alt={activeItem.title}
                        className="showcase-main-image"
                    />
                    <div className="showcase-overlay">
                        <h2>{activeItem.title}</h2>
                        <div className="showcase-price">
                            {activeItem.price === 0
                                ? "Free"
                                : `$${activeItem.price}`}
                        </div>
                    </div>
                    </NavLink>
                </div>

            <div className="showcase-side">
                {items.map((item, index) => (
                    <div
                        key={item.id}
                        className={`showcase-thumb ${
                            index === activeIndex ? "active" : ""
                        }`}
                        onClick={() => setActiveIndex(index)}
                    >
                        <img src={`http://localhost:8080${item.imageUrl}`} alt={item.title}/>
                        <span>{item.title}</span>
                    </div>
                ))}
            </div>
        </div>
    );
};
