import { Link } from "react-router-dom";
import "./NewsCard.css";

interface NewsCardProps {
    news: NewsDto;
}

export const NewsCard = ({ news }: NewsCardProps) => {
    return (
        <article className="news-card">
            <Link to={`/news/${news.id}`} className="news-card__image-wrapper">
                <img
                    src={`http://localhost:8080${news.imageUrl}`}
                    alt={news.title}
                    className="news-card__image"
                />
            </Link>

            <div className="news-card__body">
                <div className="news-card__meta">
                    {new Date(news.createdAt).toLocaleDateString()}
                </div>

                <h2 className="news-card__title">
                    <Link to={`/news/${news.id}`}>
                        {news.title}
                    </Link>
                </h2>

                <p className="news-card__excerpt">
                    {news.content.substring(0, 140)}...
                </p>

                <Link
                    to={`/news/${news.id}`}
                    className="news-card__more"
                >
                    Подробнее →
                </Link>
            </div>
        </article>
    );
};
