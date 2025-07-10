import React, { useEffect, useState } from 'react';
import './GameForm.css';

function GameForm({ initialData = {}, onSubmit, submitText = 'Сохранить' }) {
    const [title, setTitle] = useState(initialData.title || '');
    const [details, setDetails] = useState(initialData.details || '');
    const [selectedCategoryIds, setSelectedCategoryIds] = useState(initialData.categoryIds || []);
    const [imageFile, setImageFile] = useState(null);
    const [gameFile, setGameFile] = useState(null);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/categories', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            },
        })
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(err => console.error('Ошибка при загрузке категорий:', err));
    }, []);

    const handleCategoryClick = (id) => {
        setSelectedCategoryIds(prev =>
            prev.includes(id)
                ? prev.filter(cid => cid !== id)
                : [...prev, id]
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('title', title);
        formData.append('details', details);
        selectedCategoryIds.forEach(id => formData.append('categoryId', id));
        if (imageFile) formData.append('imageFile', imageFile);
        if (gameFile) formData.append('gameFile', gameFile);

        onSubmit(formData);
    };

    return (
        <form className="game-form" onSubmit={handleSubmit}>
            <h2>{submitText}</h2>

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

            <div className="category-list">
                <label>Категории:</label>
                <div className="category-options">
                    {categories.map(cat => (
                        <div
                            key={cat.id}
                            className={`category-option ${selectedCategoryIds.includes(cat.id) ? 'selected' : ''}`}
                            onClick={() => handleCategoryClick(cat.id)}
                        >
                            {cat.title}
                        </div>
                    ))}
                </div>
            </div>

            <label>Изображение:</label>
            <input type="file" accept="image/*" onChange={e => setImageFile(e.target.files[0])} />

            <label>Файл игры (zip):</label>
            <input type="file" accept=".zip" onChange={e => setGameFile(e.target.files[0])} />

            <button type="submit">{submitText}</button>
        </form>
    );
}

export default GameForm;
