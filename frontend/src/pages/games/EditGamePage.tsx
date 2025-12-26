import React, {useEffect, useState} from 'react';
import {useParams, useNavigate, data} from 'react-router-dom';
import {GameForm} from './GameForm';
import './GameForm.css';
import {editGame} from "../../services/games/editGame";
import {getGame} from "../../services/games/getGame";
import {GameFullDto} from "../../types/GameFullDto";

export const EditGamePage = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [initialData, setInitialData] = useState<GameFullDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchGame = async () => {
            setLoading(true);
            const result = await getGame(id);

            if (!result.success) {
                setError(result.error.message);
                setInitialData(null);
            } else {
                setInitialData(result.data);
                setError(null);
            }

            setLoading(false);
        };
        fetchGame();
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
