import {useEffect, useState} from "react";
import {useLocation, useParams, useSearchParams} from "react-router-dom";
import "./CategoryPage.css";
import {GameCard} from "../components/GameCard";
import {getCategory} from "../services/categories/getCategory";

export const CategoryPage = () => {
    const { categoryId } = useParams<{ categoryId: string }>();
    const categoryIdNum = categoryId ? Number(categoryId) : 0;
    const location = useLocation();
    const categoryTitle = location.state?.categoryTitle || "Категория";
    const [searchParams, setSearchParams] = useSearchParams();
    const pageFromUrl = Number(searchParams.get("page") || 0);

    const [games, setGames] = useState([]);
    const [page, setPage] = useState(pageFromUrl);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const fetchCategory = async () => {
            const res = await getCategory(categoryIdNum, page, 20);
            if (!res.success) {
                console.log(res.error.message);
            } else {
                setGames(res.data.cardItems);
                setTotalPages(res.data.totalPages);
            }
            setLoading(false);
        }
        void fetchCategory();
    }, [categoryId, page]);

    const goToPage = (newPage: number) => {
        setPage(newPage);
        setSearchParams(prev => {
            return { ...Object.fromEntries(prev), page: newPage.toString() };
        });
    };


    return (
        <div className="category-page">
            <h2 className="category-title">Категория: {categoryTitle}</h2>

            {loading ? (
                <p>Загрузка игр...</p>
            ) : (
                <div className="games-grid">
                    {games.map((game) => (
                        <GameCard key={game.id} game={game}/>
                    ))}
                </div>
            )}

            <div className="pagination">
                <button disabled={page === 0} onClick={() => goToPage(page - 1)}>
                    Назад
                </button>
                <span>
          {page + 1} / {totalPages}
        </span>
                <button
                    disabled={page + 1 === totalPages}
                    onClick={() => goToPage(page + 1)}
                >
                    Вперед
                </button>
            </div>
        </div>
    );
};
