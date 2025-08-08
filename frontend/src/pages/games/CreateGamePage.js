import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './GameForm.css';

function CreateGamePage() {
    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const defaultCategoryId = queryParams.get('categoryId');

    const [categories, setCategories] = useState([]);
    const [selectedCategoryIds, setSelectedCategoryIds] = useState(defaultCategoryId ? [parseInt(defaultCategoryId)] : []);
    const [title, setTitle] = useState('');
    const [details, setDetails] = useState('');
    const [price, setPrice] = useState(''); // строка, чтобы можно было очистить поле
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
                if (!defaultCategoryId && data.length > 0) {
                    setSelectedCategoryIds([data[0].id]);
                }
            })
            .catch(err => {
                console.error('Ошибка при загрузке категорий:', err);
            });
    }, [defaultCategoryId]);

    const handleCategoryClick = (id) => {
        setSelectedCategoryIds(prev =>
            prev.includes(id)
                ? prev.filter(cid => cid !== id)
                : [...prev, id]
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (selectedCategoryIds.length === 0) {
            alert('Пожалуйста, выберите хотя бы одну категорию');
            return;
        }
        if (!imageFile || !gameFile) {
            alert('Пожалуйста, загрузите изображение и файл игры');
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
        formData.append('price', price.toString()); // цена в строку
        selectedCategoryIds.forEach(id => formData.append('categoryId', id));
        formData.append('imageFile', imageFile);
        formData.append('gameFile', gameFile);

        try {
            const res = await fetch('http://localhost:8080/api/game/new_game', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
                },
                body: formData,
            });

            if (res.ok) {
                alert('Игра успешно создана');
                navigate('/home');
            } else {
                const errorText = await res.text();
                alert('Ошибка: ' + errorText);
            }
        } catch (err) {
            alert('Ошибка сети: ' + err.message);
        }
    };

    return (
        <div className="form-container">
            <h2>Создать игру</h2>
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
                    required
                />

                <label>Файл игры (zip):</label>
                <input
                    type="file"
                    accept=".zip"
                    onChange={e => setGameFile(e.target.files[0])}
                    required
                />

                <button type="submit">Создать</button>
            </form>
        </div>
    );
}

export default CreateGamePage;
