import React, { useState, useEffect } from 'react';
import './GameForm.css';

function GameForm({ initialData = {}, onSubmit, submitText }) {
    const [title, setTitle] = useState(initialData.title || '');
    const [details, setDetails] = useState(initialData.details || '');
    const [price, setPrice] = useState(initialData.price != null ? initialData.price.toString() : '');
    const [categories, setCategories] = useState([]);
    const [selectedCategoryIds, setSelectedCategoryIds] = useState(initialData.categoryIds || []);
    const [imageFile, setImageFile] = useState(null);
    const [gameFile, setGameFile] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/categories', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            },
        })
            .then(res => res.json())
            .then(data => {
                setCategories(data);
                // Если при редактировании нет выбранных категорий, то выставим первую
                if (selectedCategoryIds.length === 0 && data.length > 0) {
                    setSelectedCategoryIds([data[0].id]);
                }
            })
            .catch(err => {
                console.error('Ошибка при загрузке категорий:', err);
            });
    }, []);

    // Обновляем поля, если initialData изменится (например, при загрузке из EditGamePage)
    useEffect(() => {
        setTitle(initialData.title || '');
        setDetails(initialData.details || '');
        setPrice(initialData.price != null ? initialData.price.toString() : '');
        setSelectedCategoryIds(initialData.categoryIds || []);
    }, [initialData]);

    const handleCategoryClick = (id) => {
        setSelectedCategoryIds(prev =>
            prev.includes(id)
                ? prev.filter(cid => cid !== id)
                : [...prev, id]
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (selectedCategoryIds.length === 0) {
            alert('Пожалуйста, выберите хотя бы одну категорию');
            return;
        }

        if (price === '') {
            alert('Пожалуйста, введите цену');
            return;
        }
        if (isNaN(price) || Number(price) < 0) {
            alert('Цена должна быть неотрицательным числом');
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('details', details);
        formData.append('price', price.toString());
        selectedCategoryIds.forEach(id => formData.append('categoryId', id));

        // Файлы добавляем только если они загружены (чтобы не перезаписывать при редактировании без изменения)
        if (imageFile) formData.append('imageFile', imageFile);
        if (gameFile) formData.append('gameFile', gameFile);

        onSubmit(formData);
    };

    return (
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

            <label>Категории (можно выбрать несколько):</label>
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

            <input
                type="number"
                placeholder="Цена"
                min="0"
                step="0.01"
                value={price}
                onChange={e => setPrice(e.target.value)}
                required
            />

            <label>Изображение (jpg, png):</label>
            <input
                type="file"
                accept="image/*"
                onChange={e => setImageFile(e.target.files[0])}
            />

            <label>Файл игры (zip):</label>
            <input
                type="file"
                accept=".zip"
                onChange={e => setGameFile(e.target.files[0])}
            />

            <button type="submit">{submitText || 'Сохранить'}</button>
        </form>
    );
}

export default GameForm;
