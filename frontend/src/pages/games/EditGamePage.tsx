import React, {useEffect, useState} from 'react';
import {useParams, useNavigate, data} from 'react-router-dom';
import {GameForm} from './GameForm';
import './GameForm.css';
import {editGame} from "../../services/games/editGame";
import {getGame} from "../../services/games/getGame";
import {mapGameFullDto} from "../../utils/mappers/mapGameFullDto";
import {GameFullDto} from "../../types/GameFullDto";

export const EditGamePage = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [initialData, setInitialData] = useState<GameFullDto | null>(null);

    useEffect(() => {
        getGame(id)
            .then(dto => setInitialData(mapGameFullDto(dto)))
            .catch(err => {
                console.error(err);
                alert('Не удалось загрузить данные игры');
            });
    }, [id]);

    const handleSubmit = (formData) => {
        editGame(formData, navigate, id)
    };

    if (!initialData) return <p style={{textAlign: 'center'}}>Загрузка...</p>;

    return (
        <div className="game-form-container">
            <GameForm initialData={initialData} onSubmit={handleSubmit} submitText="Сохранить"/>
        </div>
    );
}
