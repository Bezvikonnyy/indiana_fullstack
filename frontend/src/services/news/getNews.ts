import {request} from "../../api/httpClient";

export const getNews = async (newsId: number) => {
    return request<NewsDto>(`/api/news/${newsId}`);
}