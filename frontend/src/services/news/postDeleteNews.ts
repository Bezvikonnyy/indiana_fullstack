import {request} from "../../api/httpClient";

export const postDeleteNews = async (newsId: string) => {
    return request<void>(`/api/news/delete/${newsId}`, {
        method: 'DELETE',
    });
}