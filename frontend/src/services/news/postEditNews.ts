import {request} from "../../api/httpClient";

export const postEditNews = async (newsId: number) => {
    return request<NewsDto>(`/api/news/edit/${newsId}`, {
        method: 'POST',
    });
}