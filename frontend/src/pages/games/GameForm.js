import React, { useEffect, useState } from 'react';
import './GameForm.css';

function GameForm({ initialData = {}, onSubmit, submitText = 'Сохранить' }) {
    const [title, setTitle] = useState(initialData.title || '');
    const [description, setDescription] = useState(initialData.description || '');
    const [categoryId, setCategoryId] = useState(initialData.categoryId || '');
    const [imageFile, setImageFile] = useState(null);
    const [gameFile, setGameFile] = useState(null);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/categories')
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('title', title);
        formData.append('description', description);
        formData.append('categoryId', categoryId);
        if (imageFile) formData.append('image', imageFile);
        if (gameFile) formData.append('game', gameFile);

        onSubmit(formData);
    };

    return (
        <form className="game-form" onSubmit={handleSubmit}>
            <h2 className="form-title">{submitText}</h2>

            <input
                className="form-input"
                type="text"
                placeholder="Название игры"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
            />

            <textarea
                className="form-textarea"
                placeholder="Описание игры"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={4}
                required
            />

            <select
                className="form-select"
                value={categoryId}
                onChange={(e) => setCategoryId(e.target.value)}
                required
            >
                <option value="">-- Выберите категорию --</option>
                {categories.map(cat => (
                    <option key={cat.id} value={cat.id}>{cat.title}</option>
                ))}
            </select>

            <div className="form-file">
                <label>Изображение:</label>
                <input type="file" accept="image/*" onChange={(e) => setImageFile(e.target.files[0])} />
            </div>

            <div className="form-file">
                <label>Файл игры (zip):</label>
                <input type="file" accept=".zip" onChange={(e) => setGameFile(e.target.files[0])} />
            </div>

            <button className="form-button" type="submit">{submitText}</button>
        </form>
    );
}

export default GameForm;
