import React, {useEffect, useState} from "react";
import "./NewsShowCaseSection.css";
import {NavLink} from "react-router-dom";

interface ShowcaseSectionProps {
    items: NewsDto[];
    autoPlay?: boolean;
    interval?: number;
}

export const NewsShowCaseSection: React.FC<ShowcaseSectionProps> = ({
                                                                    items,
                                                                    autoPlay = true,
                                                                    interval = 10000
                                                                }) => {
    const [activeNewsIndex, setNewsActiveIndex] = useState(0);

    useEffect(() => {
        if (!autoPlay || items.length <= 1) return;

        const timer = setInterval(() => {
            setNewsActiveIndex(prev =>
                prev === items.length - 1 ? 0 : prev + 1
            );
        }, interval ?? 10000);

        return () => clearInterval(timer);
    }, [items.length, autoPlay, interval]);

    if (!items || items.length === 0) return null;

    const activeItem = items[activeNewsIndex];

    return (
        <div className="showcase">
            <div className="showcase-main">
                <NavLink to={`/news/${activeItem.id}`}>
                    <img
                        src={`http://localhost:8080${activeItem.imageUrl}`}
                        alt={activeItem.title}
                        className="showcase-main-image"
                    />
                    <div className="showcase-overlay">
                        <h2>{activeItem.title}</h2>
                    </div>
                </NavLink>
            </div>

            <div className="showcase-side">
                {items.map((item, index) => (
                    <div
                        key={item.id}
                        className={`showcase-thumb ${
                            index === activeNewsIndex ? "active" : ""
                        }`}
                        onClick={() => setNewsActiveIndex(index)}
                    >
                        <img src={`http://localhost:8080${item.imageUrl}`} alt={item.title}/>
                        <span>{item.title}</span>
                    </div>
                ))}
            </div>
        </div>
    );
};
