import {request} from "../../api/httpClient";
import {NewsDto} from "../../types/news/NewsDto";

export const getNews = async (newsId: number) => {
    return request<NewsDto>(`/api/news/${newsId}`);
}
