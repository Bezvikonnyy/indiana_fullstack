import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import {getAllNews} from "../../services/news/getAllNews";
import {NewsCard} from "../../components/NewsCard";
import './AllNews.css';

export const AllNews = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const pageFromUrl = Number(searchParams.get("page") || 0);

    const [newsPage, setNewsPage] = useState<PageResponse<NewsDto> | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        getAllNews(pageFromUrl)
            .then(response => {
                if (response.success) {
                    setNewsPage(response.data);
                } else {
                    console.error(response.error.message);
                }
            })
            .finally(() => setLoading(false));

    }, [pageFromUrl]);

    const goToPage = (page: number) => {
        setSearchParams({ page: page.toString() });
    };

    if (loading) return <div>Loading...</div>;
    if (!newsPage) return null;

    return (
        <div className="all-news">
            <h1 className="all-news__title">News</h1>

            <div className="news-grid">
                {newsPage.content.map(news => (
                    <NewsCard key={news.id} news={news} />
                ))}
            </div>

            <div className="pagination">
                <button
                    disabled={newsPage.first}
                    onClick={() => goToPage(pageFromUrl - 1)}
                >
                    Prev
                </button>

                {Array.from({ length: newsPage.totalPages }).map((_, i) => (
                    <button
                        key={i}
                        onClick={() => goToPage(i)}
                        className={i === pageFromUrl ? "active" : ""}
                    >
                        {i + 1}
                    </button>
                ))}

                <button
                    disabled={newsPage.last}
                    onClick={() => goToPage(pageFromUrl + 1)}
                >
                    Next
                </button>
            </div>
        </div>
    );
};
