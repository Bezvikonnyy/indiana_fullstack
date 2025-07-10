import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './GameForm.css';

function EditGamePage() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [categories, setCategories] = useState([]);
    const [title, setTitle] = useState('');
    const [details, setDetails] = useState('');
    const [selectedCategoryId, setSelectedCategoryId] = useState('');
    const [imageFile, setImageFile] = useState(null);
    const [gameFile, setGameFile] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8080/api/game/${id}`)
            .then(res => res.json())
            .then(data => {
                setTitle(data.title || '');
                setDetails(data.details || '');
                setSelectedCategoryId(data.categories?.[0]?.id || '');
                setLoading(false);
            })
            .catch(err => {
                alert('Ошибка загрузки игры');
                setLoading(false);
            });

        fetch('http://localhost:8080/api/categories')
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!selectedCategoryId) {
            alert('Пожалуйста, выберите категорию');
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('details', details);
        formData.append('categoryId', selectedCategoryId);

        if (imageFile) formData.append('imageFile', imageFile);
        if (gameFile) formData.append('gameFile', gameFile);

        const token = localStorage.getItem('token');

        try {
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

    if (loading) return <p style={{ textAlign: 'center', marginTop: '2rem' }}>Загрузка...</p>;

    return (
        <div className="form-container">
            <h2>Редактировать игру</h2>
            <form className="game-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Название игры"
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                    required
                />

                <textarea
                    placeholder="Описание игры"
                    value={details}
                    onChange={e => setDetails(e.target.value)}
                    rows={4}
                    required
                />

                <select
                    value={selectedCategoryId}
                    onChange={e => setSelectedCategoryId(e.target.value)}
                    required
                >
                    <option value="">-- Выберите категорию --</option>
                    {categories.map(cat => (
                        <option key={cat.id} value={cat.id}>{cat.title}</option>
                    ))}
                </select>

                <label>Новое изображение (оставьте пустым, если не менять):</label>
                <input
                    type="file"
                    accept="image/*"
                    onChange={e => setImageFile(e.target.files[0])}
                />

                <label>Новый файл игры (zip) (оставьте пустым, если не менять):</label>
                <input
                    type="file"
                    accept=".zip"
                    onChange={e => setGameFile(e.target.files[0])}
                />

                <button type="submit">Сохранить</button>
            </form>
        </div>
    );
}

export default EditGamePage;
