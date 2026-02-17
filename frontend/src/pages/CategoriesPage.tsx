import { useEffect, useState } from "react";
import {Link, useSearchParams} from "react-router-dom";
import { getCategoriesPage } from "../services/categories/getCategoriesPage";
import { CategoriesForPage } from "../types/CategoriesForPage";
import { GameCard } from "../components/GameCard";
import "./CategoriesPage.css";

export const CategoriesPage = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const pageFromUrl = Number(searchParams.get("page") || 0);

    const [data, setData] = useState<CategoriesForPage | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            const res = await getCategoriesPage(pageFromUrl, 11);
            if (res.success) {
                setData(res.data);
            } else {
                console.error(res.error.message);
            }
        };
        void fetchData();
    }, [pageFromUrl]);

    if (!data) return <div className="loading">Loading...</div>;

    return (
        <div className="categories-page">
            {data.categories.map(category => (
                <div key={category.id} className="categories-page__category">
                    <h2 className="categories-page__category-title">
                        <Link to={`/category/${category.id}`} state={{ categoryTitle: category.title }}>
                            {category.title}
                        </Link>
                    </h2>

                    <div className="categories-page__games-list">
                        {category.games.map(game => (
                            <GameCard key={game.id} game={game} />
                        ))}
                    </div>
                </div>
            ))}

            <div className="categories-page__pagination">
                <button
                    disabled={data.page === 0}
                    onClick={() => setSearchParams({ page: String(data?.page - 1) })}
                    className="categories-page__pagination-btn"
                >
                    Prev
                </button>

                <span className="categories-page__pagination-info">
                    {data.page + 1} / {data.totalPages}
                </span>

                <button
                    disabled={data.page + 1 >= data.totalPages}
                    onClick={() => setSearchParams({ page: String(data?.page + 1) })}
                    className="categories-page__pagination-btn"
                >
                    Next
                </button>
            </div>
        </div>
    );
};
