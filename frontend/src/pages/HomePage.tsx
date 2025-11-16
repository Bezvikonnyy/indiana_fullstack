import './HomePage.css';
import {NavLink} from "react-router-dom";

export const HomePage = () => {

    return (
        <div className="homePage">
            <div className="linkContainerHomePage">
                <NavLink to="">Все игры</NavLink>
                <NavLink to="">Новинки</NavLink>
                <NavLink to="">Акции</NavLink>
                <NavLink to="">Популярные</NavLink>
                <NavLink to="">Новости</NavLink>
            </div>
            <div className="gameRecommendationHomePage">Фото</div>
        </div>
    );
}
