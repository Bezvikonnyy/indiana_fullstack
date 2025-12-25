import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {getUserId, hasRole} from '../../utils/auth';
import {CommentSection} from '../../components/CommentSection';
import {FavoriteButton} from '../../components/buttons/FavoriteButton';
import './GameDetailsPage.css';
import {CartButton} from "../../components/buttons/CartButton";
import {DeleteGameButton} from "../../components/buttons/DeleteGameButton";
import {EditGameButton} from "../../components/buttons/EditGameButton";
import {DownloadGameButton} from "../../components/buttons/DownloadGameButton";
import {mapGameFullDto} from "../../utils/mappers/mapGameFullDto";
import {getGame} from "../../services/games/getGame";
import {GameFullDto} from "../../types/GameFullDto";
import {PurchasedStatus} from "../../components/buttons/PurchasedStatus";

export const GameDetailsPage = () => {
    const {id} = useParams();
    const [game, setGame] = useState<GameFullDto | null>(null);
    const currentUserId = getUserId();

    useEffect(() => {
        getGame(id)
            .then(dto => setGame(mapGameFullDto(dto)))
            .catch(err => {
                console.error(err);
                alert('Не удалось загрузить данные игры');
            });
    }, [id]);

    if (!game) return <p className="loading-text">Загрузка...</p>;

    const isLoggedIn = !!localStorage.getItem('token');
    const isAuthor = game.authorId === currentUserId;
    const isAdmin = hasRole(['ADMIN']);

    return (
        <>
            <div className="game-details-container">
                <div className="left-container-game-details">

                    <img
                        src={`http://localhost:8080${game.imageUrl}`}
                        alt={game.title}
                        className="game-image-game-details"
                    />
                    {isLoggedIn && (
                        <div className="game-buttons-game-details">
                            <div>
                                <DownloadGameButton fileUrl={game.gameFileUrl}/>
                            </div>
                            {(isAuthor || isAdmin) && (
                                <>
                                    <EditGameButton id={id}/>
                                    <DeleteGameButton id={id}/>
                                </>
                            )}
                            <div>
                                <CartButton gameId={game.id} isInCart={game.isInCart}/>
                            </div>
                            <div>
                                <FavoriteButton gameId={game.id} isFavorite={game.isFavorite}/>
                            </div>
                            <div>
                                <PurchasedStatus isPurchased={game.isPurchased}/>
                            </div>
                        </div>
                    )}

                </div>
                <div className="right-container-game-details">
                    <h2 className="game-title-game-details">{game.title}</h2>

                    <p className="game-description-game-details">{game.details}</p>

                    <p className="game-categories-game-details">
                        <strong>Категории: </strong>
                        {Array.isArray(game.categories) && game.categories.length > 0
                            ? game.categories.map(cat => cat.title).join(', ')
                            : 'Нет категорий'}
                    </p>

                    <p className="game-price-game-details">
                        <strong>Цена: </strong>
                        {game.price ? `${game.price} грн` : 'Бесплатно'}
                    </p>
                </div>
            </div>
            <div className="comments-section-game-details">
                <CommentSection gameId={id}/>
            </div>
        </>
    );
}
