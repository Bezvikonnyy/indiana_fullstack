import React, {FC, useEffect, useState} from 'react';
import './NewsForm.css';

interface NewsFormPageProps {
    initialData?: Partial<NewsDto>;
    onSubmit: (formData: FormData) => void;
    submitText?: string;
}

export const NewsFormPage: FC<NewsFormPageProps> = ({initialData, onSubmit, submitText}) => {
    const [title, setTitle] = useState(initialData?.title || '');
    const [content, setContent] = useState(initialData?.content || '');
    const [imageFile, setImageFile] = useState<File | null>(null);
    const [imagePreview, setImagePreview] = useState<string | null>(initialData?.imageUrl || null);

    useEffect(() => {
        setTitle(initialData?.title || '');
        setContent(initialData?.content || '');
        setImagePreview(initialData?.imageUrl || null);
    }, [initialData]);

    const handleImageSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;
        setImageFile(file);
        setImagePreview(URL.createObjectURL(file));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        if (!title.trim()) {
            alert('Введите название новости');
            return;
        }
        if (!content.trim()) {
            alert('Введите текст новости');
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        if (imageFile) {
            formData.append('imageFile', imageFile);
        }

        onSubmit(formData);
    };

    return (
        <form className="news-form" onSubmit={handleSubmit}>
            <div className="news-form-top-container">
                <div
                    className="input-news-image"
                    onClick={() => document.getElementById('news-image-input')?.click()}
                >
                    {imagePreview ? (
                        <img src={`http://localhost:8080${imagePreview}`} className="news-image-preview" alt="preview"/>
                    ) : (
                        '+ добавить фото'
                    )}
                </div>

                <input
                    id="news-image-input"
                    type="file"
                    accept="image/*"
                    style={{display: 'none'}}
                    onChange={handleImageSelect}
                />

                <div className="news-form-top-text-container">
                    <input
                        className="input-news-title"
                        type="text"
                        placeholder="Название новости"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />

                    <textarea
                        className="input-news-content"
                        placeholder="Текст новости"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                        rows={8}
                    />
                </div>
            </div>

            <button className="button-news-save" type="submit">
                {submitText || 'Сохранить'}
            </button>
        </form>
    );
};
