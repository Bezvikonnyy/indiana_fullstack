import React, { useEffect, useState } from 'react';
import { NewsFormPage } from './NewsFormPage';
import { getNews } from '../../services/news/getNews';
import { postEditNews } from '../../services/news/postEditNews';
import {useNavigate, useParams} from 'react-router-dom';

export const EditNewsPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [news, setNews] = useState<NewsDto | null>(null);

    useEffect(() => {
        const fetchNews = async () => {
            if (!id) return;
            const res = await getNews(Number(id));
            if (!res.success) {
                console.log(res.error.message);
            } else {
                setNews(res.data);
            }
        };
        void fetchNews();
    }, [id]);

    const handleEdit = async (formData: FormData) => {
        if (!id) return;
        try {
            await postEditNews(Number(id));
            alert('Новость успешно обновлена!');
            navigate(`/news/${id}`);
        } catch (err) {
            console.error(err);
            alert('Ошибка при обновлении новости');
        }
    };

    if (!news) return <p>Загрузка...</p>;

    return (
        <div>
            <h2 className="hover-edit-news">Редактировать новость</h2>
            <NewsFormPage initialData={news} onSubmit={handleEdit} submitText="Сохранить" />
        </div>
    );
};
