import React, {useState, useEffect, FC} from 'react';
import './GameForm.css';
import {GameFullDto} from '../../types/GameFullDto';
import {getAllCategory} from "../../services/categories/getAllCategory";

interface GameFormProps {
    initialData?: Partial<GameFullDto>;
    onSubmit: (formData: FormData) => void;
    submitText?: string;
}

export const GameForm: FC<GameFormProps> = ({initialData, onSubmit, submitText}) => {
    const [title, setTitle] = useState(initialData?.title || '');
    const [details, setDetails] = useState(initialData?.details || '');
    const [price, setPrice] = useState(initialData?.price != null ? initialData.price : 0);
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(initialData?.categories || []);
    const [imageFile, setImageFile] = useState(null);
    const [imagePreview, setImagePreview] = useState<string | null>(null);
    const [gameFile, setGameFile] = useState(null);

    useEffect(() => {
        getAllCategory(setCategories, selectedCategory, setSelectedCategory);
    }, []);

    useEffect(() => {
        setTitle(initialData?.title || '');
        setDetails(initialData?.details || '');
        setPrice(initialData?.price != null ? initialData.price : 0);
        setSelectedCategory(initialData?.categories || []);
    }, [initialData]);

    const handleCategoryClick = (category) => {
        setSelectedCategory(prev =>
            prev.includes(category)
                ? prev.filter(cid => cid !== category)
                : [...prev, category]
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (selectedCategory.length === 0) {
            alert('Пожалуйста, выберите хотя бы одну категорию');
            return;
        }
        if (price === 0) {
            alert('Пожалуйста, введите цену');
            return;
        }
        if (isNaN(price) || price < 0) {
            alert('Цена должна быть неотрицательным числом');
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('details', details);
        formData.append('price', price.toString());
        selectedCategory.forEach(category => formData.append('categoryId', category.id.toString()));

        if (imageFile) formData.append('imageFile', imageFile);
        if (gameFile) formData.append('gameFile', gameFile);

        onSubmit(formData);
    };

    const handleImageSelect = (e) => {
        const file = e.target.files[0];
        if (!file) return;

        setImageFile(file);
        setImagePreview(URL.createObjectURL(file));
    };


    return (
        <form className="game-form" onSubmit={handleSubmit}>
            <div className="game-form-top-container">
                <div
                    className="input-game-image"
                    onClick={() => document.getElementById("image-input").click()}
                >
                    {imagePreview ? (
                        <img src={imagePreview} className="game-image-preview" alt="preview"/>
                    ) : (
                        "+ добавить фото"
                    )}
                </div>

                <input
                    id="image-input"
                    type="file"
                    accept="image/*"
                    style={{display: "none"}}
                    onChange={handleImageSelect}
                />
                <div className="game-form-top-text-container">
                    <input
                        className="input-game-title"
                        type="text"
                        placeholder="Название игры"
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                        required
                    />

                    <textarea
                        className="input-game-details"
                        type="text"
                        placeholder="Описание игры"
                        value={details}
                        onChange={e => setDetails(e.target.value)}
                        required
                    />
                </div>

            </div>

            <input
                className="input-game-price"
                type="number"
                placeholder="Цена"
                min="0"
                step="0.01"
                value={price}
                onChange={e => setPrice(e.target.value)}
                required
            />

            <label>Категории (можно выбрать несколько):</label>

            <div className="category-options">
                {categories.map(cat => (
                    <div
                        key={cat.id}
                        className={`category-option ${selectedCategory.some(c => c.id === cat.id) ? 'selected' : ''}`}
                        onClick={() => handleCategoryClick(cat)}
                    >
                        {cat.title}
                    </div>
                ))}
            </div>

            <label>Файл игры (zip):</label>
            <input
                type="file"
                accept=".zip"
                onChange={e => setGameFile(e.target.files[0])}
            />

            <button className="button-game-save" type="submit">{submitText || 'Сохранить'}</button>
        </form>
    );
}
