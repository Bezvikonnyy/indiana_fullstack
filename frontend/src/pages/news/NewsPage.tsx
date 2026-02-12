import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {getNews} from '../../services/news/getNews';
import './NewsForm.css';
import {getUserId, hasRole} from "../../utils/auth";
import {EditNewsButton} from "../../components/buttons/newsButtons/EditNewsButton";
import {DeleteNewsButton} from "../../components/buttons/newsButtons/DeleteNewsButton";

export const NewsPage: React.FC = () => {
    const {id} = useParams();
    const [news, setNews] = useState<NewsDto | null>(null);

    const currentUserId = getUserId();
    const isAuthor = news?.authorId === currentUserId;
    const isAdmin = hasRole(['ADMIN']);

    useEffect(() => {
        const fetchNews = async () => {
            if (!id) return;
            const res = await getNews(Number(id));
            if (!res.success) {
                alert(res.error.message);
            } else {
                setNews(res.data)
            }
        };
        void fetchNews();
    }, [id]);

    if (!news) return <p>Загрузка...</p>;

    return (
        <div className="news-page-container">
            {news.imageUrl && (
                <div className="news-image-container">
                    <img src={`http://localhost:8080${news.imageUrl}`} alt={news.title} className="news-image"/>
                    <h1 className="news-title">{news.title}</h1>
                </div>
            )}
            <div className="news-page-buttons">
                {(isAuthor || isAdmin) && (
                    <>
                        <EditNewsButton id={id}/>
                        <DeleteNewsButton id={id}/>
                    </>
                )}
            </div>
            <p className="news-content">{news.content}</p>
        </div>

    );
};
