import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import GameForm from './GameForm';
import './GameForm.css';

function EditGamePage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [initialData, setInitialData] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/game/${id}`)
            .then(res => res.json())
            .then(data => {
                setInitialData({
                    title: data.title,
                    details: data.details,
                    price: data.price,
                    categoryIds: data.categories.map(cat => cat.id),
                });
            })
            .catch(() => alert('Ошибка при загрузке данных игры'));
    }, [id]);

    const handleSubmit = async (formData) => {
        try {
            const token = localStorage.getItem('token');
            const res = await fetch(`http://localhost:8080/api/game/edit/${id}`, {
                method: 'POST',
                headers: { Authorization: `Bearer ${token}` },
                body: formData,
            });

            if (res.ok) {
                alert('Игра обновлена');
                navigate(`/games/${id}`);
            } else {
                const errorText = await res.text();
                alert('Ошибка: ' + errorText);
            }
        } catch (err) {
            alert('Ошибка сети: ' + err.message);
        }
    };

    if (!initialData) return <p style={{ textAlign: 'center' }}>Загрузка...</p>;

    return (
        <div className="form-container">
            <GameForm initialData={initialData} onSubmit={handleSubmit} submitText="Сохранить" />
        </div>
    );
}

export default EditGamePage;
