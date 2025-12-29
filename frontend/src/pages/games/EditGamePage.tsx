import React, {useEffect, useState} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
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
        void fetchGame();
    }, [id]);

    const handleSubmit = async (formData) => {
        const res = await editGame(formData, id);
        if (res.success) {
            alert('Игра обновлена');
            navigate(`/games/${id}`);
        } else {
            alert(res.error.message);
        }
    };

    if (!initialData) return <p style={{textAlign: 'center'}}>Загрузка...</p>;

    return (
        <div className="game-form-container">
            <GameForm initialData={initialData} onSubmit={handleSubmit} submitText="Сохранить"/>
        </div>
    );
}
